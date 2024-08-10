package com.codingshuttle.app.zomatoApp.strategies.impl;

import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.repositories.DeliveryExecutiveRepository;
import com.codingshuttle.app.zomatoApp.strategies.DeliveryExecutiveMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NearestTenDeliveryExecutiveMatchingStrategy implements DeliveryExecutiveMatchingStrategy {
    private final DeliveryExecutiveRepository deliveryExecutiveRepository;
    @Override
    public List<DeliveryExecutive> findMatchingDeliveryExecutives(Order order) {
        Point restaurantLocation = order.getRestaurant().getLocation();
        return deliveryExecutiveRepository.findTenNearestDeliveryExecutives(restaurantLocation);
    }
}
