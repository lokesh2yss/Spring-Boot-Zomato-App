package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;

import java.util.List;

public interface DeliveryExecutiveMatchingStrategy {
    List<DeliveryExecutive> findMatchingDeliveryExecutives(Order order);
}
