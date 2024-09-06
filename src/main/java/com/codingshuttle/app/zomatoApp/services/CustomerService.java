package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(Long customerId);

    OrderDto placeOrder(Long customerId, ConfirmOrderDto confirmOrderDto);

    OrderDto cancelOrder(Long orderId);

    AddressDto addCustomerAddress(Long customerId, AddressDto addressDto);

    AddressDto updateCustomerAddress(Long customerId, Long addressId, AddressDto addressDto);

    boolean deleteCustomerAddress(Long customerId, Long addressId);

    AddressDto getCustomerDefaultAddress(Long  customerId);

    AddressDto setCustomerDefaultAddress(Long  customerId, Long addressId);

    List<OrderDto> getCustomersAllCurrentOrders(Long  customerId);

    List<OrderDto> getCustomerOrderHistory(Long  customerId);

    boolean addFundsToWallet(Long  customerId, double amount);


    OrderStatus getCustomerOrderStatus(Long  orderId);

    Point getDeliveryExecutiveLiveLocation(Long orderId);

    boolean tipDeliveryExecutive(Long orderId, double amount);

    boolean rateDeliveryExecutive(Long orderId, Integer rating);

    boolean rateRestaurant(Long orderId, Integer rating);

    boolean rateMenuItem(Long menuItemId, Integer rating);

    ReviewDto writeRestaurantReview(ReviewDto reviewDto);


    void createNewCustomer(User user);

    OrderRequestDto addOrderItemToOrderRequest(Long customerId, OrderRequestItemDto orderRequestItemDto);

    OrderRequestDto deleteOrderItemFromOrderRequest(Long customerId, Long menuItemId);

    Page<OrderDto> getAllMyOrders(PageRequest pageRequest);
}
