package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface CustomerService {
    List<RestaurantDto> getNearbyRestaurantsByCustomer(CustomerDto customerDto);

    List<MenuItemDto> getRestaurantMenu(Long restaurantId);

    MenuItemDto addMenuItemToOrderRequest(OrderRequestDto orderRequestDto, MenuItemDto menuItemDto);

    MenuItemDto deleteMenuItemFromOrderRequest(OrderRequestDto orderRequestDto, MenuItemDto menuItemDto);

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
