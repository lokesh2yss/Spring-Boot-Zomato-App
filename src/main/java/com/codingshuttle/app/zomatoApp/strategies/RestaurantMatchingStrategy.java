package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;

import java.util.List;

public interface RestaurantMatchingStrategy {
    List<Restaurant> findMatchingRestaurants(Customer customer);
}
