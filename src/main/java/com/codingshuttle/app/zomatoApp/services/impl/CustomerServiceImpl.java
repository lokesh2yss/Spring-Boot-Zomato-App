package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.CustomerRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import com.codingshuttle.app.zomatoApp.services.OrderRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final OrderRequestService orderRequestService;

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id"+customerId)
        );
    }

    @Override
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public OrderDto cancelOrder(Long orderId) {
        return null;
    }

    @Override
    public AddressDto addCustomerAddress(Long customerId, AddressDto addressDto) {
        User user = getUserByCustomerId(customerId);
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateCustomerAddress(Long customerId, Long addressId, AddressDto addressDto) {
        User user = getUserByCustomerId(customerId);
        return addressService.updateAddressForUser(user.getId(), addressId, addressDto);
    }

    @Override
    public boolean deleteCustomerAddress(Long customerId, Long addressId) {
        User user = getUserByCustomerId(customerId);
        return addressService.deleteAddressForUser(user.getId(), addressId);
    }

    @Override
    public AddressDto getCustomerDefaultAddress(Long customerId) {
        User user = getUserByCustomerId(customerId);
        return modelMapper.map(user.getDefaultAddress(), AddressDto.class);
    }

    @Override
    public AddressDto setCustomerDefaultAddress(Long customerId, Long addressId) {
        User user = getUserByCustomerId(customerId);
        return addressService.setDefaultAddressForUser(user.getId(), addressId);
    }
    private User getUserByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id="+customerId));

        return customer.getUser();
    }

    @Override
    public List<OrderDto> getCustomersAllCurrentOrders(Long customerId) {
        return null;
    }

    @Override
    public List<OrderDto> getCustomerOrderHistory(Long customerId) {
        return null;
    }

    @Override
    public boolean addFundsToWallet(Long customerId, double amount) {
        return false;
    }

    @Override
    public OrderStatus getCustomerOrderStatus(Long orderId) {
        return null;
    }

    @Override
    public Point getDeliveryExecutiveLiveLocation(Long orderId) {
        return null;
    }

    @Override
    public boolean tipDeliveryExecutive(Long orderId, double amount) {
        return false;
    }

    @Override
    public boolean rateDeliveryExecutive(Long orderId, Integer rating) {
        return false;
    }

    @Override
    public boolean rateRestaurant(Long orderId, Integer rating) {
        return false;
    }

    @Override
    public boolean rateMenuItem(Long menuItemId, Integer rating) {
        return false;
    }

    @Override
    public ReviewDto writeRestaurantReview(ReviewDto reviewDto) {
        return null;
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
    public OrderRequestDto addOrderItemToOrderRequest(Long customerId, OrderRequestItemDto orderRequestItemDto) {
        Customer customer = getCurrentCustomer();
        log.info("Retrieved currentCustomer in addOrderItemToOrderRequest {}", customer);
        return orderRequestService.addMenuItemToOrderRequest(customer, orderRequestItemDto.getMenuItemId(), orderRequestItemDto.getQuantity());
    }

    @Override
    public OrderRequestDto deleteOrderItemFromOrderRequest(Long customerId, Long menuItemId) {
        Customer customer = getCurrentCustomer();
        log.info("Retrieved currentCustomer in addOrderItemToOrderRequest {}", customer);
        return orderRequestService.deleteMenuItemFromOrderRequest(customer, menuItemId);
    }

    private Customer getCurrentCustomer() {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUser(user).orElseThrow(() ->
                new ResolutionException("Customer not associated with user with id "+user.getId()));

    }
}
