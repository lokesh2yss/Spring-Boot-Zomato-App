package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.strategies.impl.NearbyTopRatedTenDeliveryExecutiveMatchingStrategy;
import com.codingshuttle.app.zomatoApp.strategies.impl.NearestTenDeliveryExecutiveMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryExecutiveStrategyManager {
    private final NearestTenDeliveryExecutiveMatchingStrategy nearestTenDeliveryExecutiveMatchingStrategy;
    private final NearbyTopRatedTenDeliveryExecutiveMatchingStrategy nearbyTopRatedTenDeliveryExecutiveMatchingStrategy;

    public DeliveryExecutiveMatchingStrategy deliveryExecutiveMatchingStrategy(double rating) {
        if(rating >=4.8) {
            return nearbyTopRatedTenDeliveryExecutiveMatchingStrategy;
        }
        else {
            return nearestTenDeliveryExecutiveMatchingStrategy;
        }
    }
}
