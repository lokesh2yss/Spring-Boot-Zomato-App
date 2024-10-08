package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.*;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.OrderItemRepository;
import com.codingshuttle.app.zomatoApp.repositories.OrderRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.OrderRequestService;
import com.codingshuttle.app.zomatoApp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final OrderRequestService orderRequestService;
    private final AddressService addressService;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id:"+orderId));
    }

    @Override
    public Order createNewOrder(OrderRequest orderRequest, PaymentMethod paymentMethod, Long addressId) {
        Address deliveryAddress = addressService.getAddressById(addressId);
        orderRequest.setOrderRequestStatus(OrderRequestStatus.CONFIRMED);
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setId(null);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setOrderDeliveryStatus(OrderDeliveryStatus.NOT_ASSIGNED);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryAddress(deliveryAddress);

        order.setPickupOtp(generateRandomOtp());
        order.setDeliveryOtp(generateRandomOtp());
        order.setPlacedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        orderRequest.getOrderItems()
                        .forEach(orderItem -> orderItem.setOrder(savedOrder));
        orderRequestService.update(orderRequest);

        return savedOrder;
    }

    @Override
    public Order updateOrderStatus(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllCurrentOrdersOfCustomer(Customer customer, PageRequest pageRequest) {
        return orderRepository.findByCustomerAndOrderStatusNotInAndOrderDeliveryStatusNotIn(customer, List.of(OrderStatus.CANCELLED), List.of(OrderDeliveryStatus.DELIVERED), pageRequest);
    }

    @Override
    public Page<Order> getAllCurrentOrdersOfRestaurant(Restaurant restaurant, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Order> getAllOrdersOfCustomer(Customer customer, PageRequest pageRequest) {
        return orderRepository.findByCustomer(customer, pageRequest);
    }

    @Override
    public Page<Order> getAllOrdersOfRestaurant(Restaurant restaurant, PageRequest pageRequest) {
        return orderRepository.findByRestaurant(restaurant, pageRequest);
    }

    @Override
    public Page<Order> getAllOrdersOfDeliveryExecutive(DeliveryExecutive deliveryExecutive, PageRequest pageRequest) {
        return orderRepository.findByDeliveryExecutive(deliveryExecutive, pageRequest);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    private String generateRandomOtp() {
        Random random = new Random();
        int otpInt = random.nextInt(10000); // gives an integer between 0 to 9999
        return String.format("%04d", otpInt);
    }
}
