package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AuthService {
    String login(String email, String password);

    UserDto signup(SignupDto signupDto);

    RestaurantDto onboardRestaurant(Long userId);

    DeliveryExecutiveDto onboardDeliveryExecutive(Long userId);

    Page<RestaurantDto> getAllRestaurants(PageRequest pageRequest);

    Page<DeliveryExecutiveDto> getAllDeliveryExecutive(PageRequest pageRequest);

    Page<OrderDto> getAllOrders(PageRequest pageRequest);

    boolean banDeliveryExecutive(Long deliveryExecutiveId);

    boolean banRestaurant(Long restaurantId);


}
