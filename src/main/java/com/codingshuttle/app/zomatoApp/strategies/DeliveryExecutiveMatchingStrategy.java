package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;

import java.util.List;

public interface DeliveryExecutiveMatchingStrategy {
    List<DeliveryExecutive> findMatchingDeliveryExecutives(Customer customer, Restaurant restaurant);
}
