package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.CustomerDto;
import com.codingshuttle.app.zomatoApp.dto.DeliveryExecutiveDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.repositories.RatingRepository;
import com.codingshuttle.app.zomatoApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    @Override
    public DeliveryExecutiveDto rateDeliveryExecutive(Order order, Integer rating) {
        return null;
    }

    @Override
    public CustomerDto rateCustomer(Order order, Integer rating) {
        return null;
    }

    @Override
    public RestaurantDto rateRestaurant(Order order, Integer rating) {
        return null;
    }

    @Override
    public void createNewRating(Order order) {

    }
}
