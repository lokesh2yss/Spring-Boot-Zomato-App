package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;

import java.util.List;

public interface AuthService {
    String login(String email, String password);

    UserDto signup(SignupDto signupDto);

    RestaurantDto onboardRestaurant(Long restaurantId);

    DeliveryExecutiveDto onboardDeliveryExecutive(Long deliveryExecutiveId);

    List<RestaurantDto> getAllRestaurants();

    List<DeliveryExecutiveDto> getAllDeliveryExecutive();

    List<OrderDto> getAllOrders();

    boolean banDeliveryExecutive(Long deliveryExecutiveId);

    boolean banRestaurant(Long restaurantId);


}
