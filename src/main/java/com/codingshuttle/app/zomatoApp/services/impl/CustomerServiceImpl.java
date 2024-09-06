package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.CustomerRepository;
import com.codingshuttle.app.zomatoApp.services.*;
import com.codingshuttle.app.zomatoApp.strategies.DeliveryExecutiveStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final OrderRequestService orderRequestService;
    private final OrderService orderService;
    private final DeliveryExecutiveStrategyManager deliveryExecutiveStrategyManager;
    private final RestaurantService restaurantService;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id"+customerId)
        );

        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<RestaurantDto> getNearbyRestaurants(Long customerId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        return restaurantService.getNearbyRestaurantsByCustomer(customer);
    }


    @Override
    public OrderRequestDto addOrderItemToOrderRequest(Long customerId, OrderRequestItemDto orderRequestItemDto) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        log.info("Retrieved currentCustomer in addOrderItemToOrderRequest {}", customer);
        return orderRequestService.addMenuItemToOrderRequest(customer, orderRequestItemDto.getMenuItemId(), orderRequestItemDto.getQuantity());
    }

    @Override
    public OrderRequestDto deleteOrderItemFromOrderRequest(Long customerId, Long menuItemId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        log.info("Retrieved currentCustomer in addOrderItemToOrderRequest {}", customer);
        return orderRequestService.deleteMenuItemFromOrderRequest(customer, menuItemId);
    }

    @Override
    public OrderDto placeOrder(Long customerId, ConfirmOrderDto confirmOrderDto) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        OrderRequest orderRequest = orderRequestService.findOrderRequestByCustomerAndRequestStatus(customer, OrderRequestStatus.PENDING);

        if(!orderRequest.getCustomer().equals(customer)) {
            throw new RuntimeConflictException("Order cannot be created as the orderRequest is not owned by the customer with id: "+customer.getId());
        }

        Order savedOrder = orderService.createNewOrder(orderRequest, confirmOrderDto.getPaymentMethod(), confirmOrderDto.getAddressId());

        deliveryExecutiveStrategyManager.deliveryExecutiveMatchingStrategy(customer.getRating()).findMatchingDeliveryExecutives(savedOrder);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto cancelOrder(Long customerId, Long orderId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        Order order = orderService.getOrderById(orderId);

        if(!order.getCustomer().equals(customer)) {
            throw new RuntimeConflictException("Order cannot be cancelled as the order is not owned by the customer with id: "+customer.getId());
        }

        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.NOT_ASSIGNED)) {
            throw new RuntimeConflictException("Order cannot be cancelled as delivery executive already assigned to this order with id: "+orderId);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        Order updatedOrder = orderService.updateOrder(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }
    @Override
    public Page<OrderDto> getCustomersAllCurrentOrders(Long customerId, PageRequest pageRequest) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        return orderService.getAllCurrentOrdersOfCustomer(customer, pageRequest)
                .map((element) -> modelMapper.map(element, OrderDto.class));
    }

    @Override
    public OrderStatusDto getCustomerOrderStatus(Long customerId, Long orderId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        Order order = orderService.getOrderById(orderId);
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setOrderStatus(order.getOrderStatus());
        orderStatusDto.setOrderDeliveryStatus(order.getOrderDeliveryStatus());
        return orderStatusDto;
    }

    @Override
    public boolean tipDeliveryExecutive(Long customerId, Long orderId, BigDecimal amount) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        Order order = orderService.getOrderById(orderId);
        if(!order.getCustomer().equals(customer)) {
            throw new RuntimeConflictException("The customer with id: "+ customer.getId()+ "cannot give tip to " +
                    "delivery executive associated with order with id: "+order.getId());
        }
        paymentService.processTip(customer, order.getDeliveryExecutive(), amount);
        return true;
    }

    @Override
    public DeliveryExecutiveDto rateDeliveryExecutive(Long customerId, Long orderId, Integer rating) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        Order order = orderService.getOrderById(orderId);
        if(!order.getCustomer().equals(customer)) {
            throw new RuntimeConflictException("The customer cannot rate as the " +
                    "order doesn't belong to customer with id: "+customer.getId());
        }
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERED)) {
            throw new RuntimeConflictException("Order has not been delivered yet"+
                    ", so cannot start rating, status: "+order.getOrderDeliveryStatus());
        }
        return ratingService.rateDeliveryExecutive(order, rating);
    }

    @Override
    public RestaurantDto rateRestaurant(Long customerId, Long orderId, Integer rating) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        Order order = orderService.getOrderById(orderId);
        if(!order.getCustomer().equals(customer)) {
            throw new RuntimeConflictException("The customer cannot rate as the " +
                    "order doesn't belong to customer with id: "+customer.getId());
        }
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERED)) {
            throw new RuntimeConflictException("Order has not been delivered yet"+
                    ", so cannot start rating, status: "+order.getOrderDeliveryStatus());
        }
        return ratingService.rateRestaurant(order, rating);
    }

    @Override
    public ReviewDto writeRestaurantReview(Long customerId,ReviewDto reviewDto) {
        return null;
    }

    @Override
    public boolean addFundsToWallet(Long customerId, double amount) {
        return false;
    }

    @Override
    public Page<OrderDto> getAllMyOrders(PageRequest pageRequest) {
        Customer customer = getCurrentCustomer();
        return orderService.getAllOrdersOfCustomer(customer, pageRequest)
                .map((element) -> modelMapper.map(element, OrderDto.class));
    }

    @Override
    public void createNewCustomer(User user) {
        Customer toSaveCustomer = Customer.builder()
                        .user(user)
                        .rating(0.0)
                        .build();
        customerRepository.save(toSaveCustomer);

    }

    @Override
    public AddressDto addCustomerAddress(Long customerId, AddressDto addressDto) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        User user = customer.getUser();
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateCustomerAddress(Long customerId, Long addressId, AddressDto addressDto) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        User user = customer.getUser();
        return addressService.updateAddressForUser(user.getId(), addressId, addressDto);
    }

    @Override
    public boolean deleteCustomerAddress(Long customerId, Long addressId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        User user = customer.getUser();
        return addressService.deleteAddressForUser(user.getId(), addressId);
    }

    @Override
    public AddressDto getCustomerDefaultAddress(Long customerId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        User user = customer.getUser();
        return modelMapper.map(user.getDefaultAddress(), AddressDto.class);
    }

    @Override
    public AddressDto setCustomerDefaultAddress(Long customerId, Long addressId) {
        Customer customer = getCurrentCustomer();
        validateCustomer(customer, customerId);
        User user = customer.getUser();
        return addressService.setDefaultAddressForUser(user.getId(), addressId);
    }

    private void validateCustomer(Customer customer, Long customerId) {
        if(!customer.getId().equals(customerId)) {
            throw new RuntimeConflictException("Customer not logged in with id:  "+customerId);
        }
    }
    private Customer getCurrentCustomer() {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUser(user).orElseThrow(() ->
                new ResolutionException("Customer not associated with user with id "+user.getId()));

    }
    private User getUserByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id="+customerId));

        return customer.getUser();
    }
}
