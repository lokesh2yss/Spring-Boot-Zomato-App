package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.CustomerDto;
import com.codingshuttle.app.zomatoApp.dto.DeliveryExecutiveDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.Order;

public interface RatingService {
    DeliveryExecutiveDto rateDeliveryExecutive(Order order, Integer rating);

    CustomerDto rateCustomer(Order order, Integer rating);

    RestaurantDto rateRestaurant(Order order, Integer rating);

    void createNewRating(Order order);
}
