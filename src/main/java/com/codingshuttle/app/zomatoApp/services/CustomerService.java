package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.OrderRequestDto;
import com.codingshuttle.app.zomatoApp.dto.ReviewDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(Long customerId);

    OrderDto placeOrder(OrderRequestDto orderRequestDto);

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
}
