package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    int PAGE_SIZE = 10;

    CustomerDto getCustomerById(Long customerId);

    List<RestaurantDto> getNearbyRestaurants(Long customerId);

    OrderRequestDto addOrderItemToOrderRequest(Long customerId, OrderRequestItemDto orderRequestItemDto);

    OrderRequestDto deleteOrderItemFromOrderRequest(Long customerId, Long menuItemId);

    OrderDto placeOrder(Long customerId, ConfirmOrderDto confirmOrderDto);

    OrderDto cancelOrder(Long customerId, Long orderId);

    OrderStatusDto getCustomerOrderStatus(Long customerId, Long  orderId);

    boolean tipDeliveryExecutive(Long customerId, Long orderId, BigDecimal amount);

    DeliveryExecutiveDto rateDeliveryExecutive(Long customerId, Long orderId, Integer rating);

    RestaurantDto rateRestaurant(Long customerId, Long orderId, Integer rating);

    ReviewDto writeRestaurantReview(Long customerId, ReviewDto reviewDto);

    Page<OrderDto> getAllMyOrders(PageRequest pageRequest);

    boolean addFundsToWallet(Long  customerId, double amount);

    AddressDto addCustomerAddress(Long customerId, AddressDto addressDto);

    AddressDto updateCustomerAddress(Long customerId, Long addressId, AddressDto addressDto);

    boolean deleteCustomerAddress(Long customerId, Long addressId);

    AddressDto getCustomerDefaultAddress(Long  customerId);

    AddressDto setCustomerDefaultAddress(Long  customerId, Long addressId);

    Page<OrderDto> getCustomersAllCurrentOrders(Long customerId, PageRequest pageRequest);

    void createNewCustomer(User user);
}
