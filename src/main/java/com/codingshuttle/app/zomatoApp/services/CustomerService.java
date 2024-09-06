package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {

    int PAGE_SIZE = 10;

    CustomerDto getCustomerById(Long customerId);

    List<RestaurantDto> getNearbyRestaurants(Long customerId);

    OrderRequestDto addOrderItemToOrderRequest(Long customerId, OrderRequestItemDto orderRequestItemDto);

    OrderRequestDto deleteOrderItemFromOrderRequest(Long customerId, Long menuItemId);

    OrderDto placeOrder(Long customerId, ConfirmOrderDto confirmOrderDto);

    OrderDto cancelOrder(Long customerId, Long orderId);

    OrderStatus getCustomerOrderStatus(Long  orderId);

    boolean tipDeliveryExecutive(Long orderId, double amount);

    boolean rateDeliveryExecutive(Long orderId, Integer rating);

    boolean rateRestaurant(Long orderId, Integer rating);

    boolean rateMenuItem(Long menuItemId, Integer rating);

    ReviewDto writeRestaurantReview(ReviewDto reviewDto);

    Page<OrderDto> getAllMyOrders(PageRequest pageRequest);

    AddressDto addCustomerAddress(Long customerId, AddressDto addressDto);

    AddressDto updateCustomerAddress(Long customerId, Long addressId, AddressDto addressDto);

    boolean deleteCustomerAddress(Long customerId, Long addressId);

    AddressDto getCustomerDefaultAddress(Long  customerId);

    AddressDto setCustomerDefaultAddress(Long  customerId, Long addressId);

    Page<OrderDto> getCustomersAllCurrentOrders(Long customerId, PageRequest pageRequest);

    boolean addFundsToWallet(Long  customerId, double amount);
    void createNewCustomer(User user);
}
