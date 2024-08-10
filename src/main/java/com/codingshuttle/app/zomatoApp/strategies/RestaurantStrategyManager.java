package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.strategies.impl.RestaurantMatchingNearbyTopRatedTwentyRestaurantStrategy;
import com.codingshuttle.app.zomatoApp.strategies.impl.RestaurantMatchingNearestTwentyRestaurantStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantStrategyManager {
    private final RestaurantMatchingNearestTwentyRestaurantStrategy nearestTwentyRestaurantStrategy;
    private final RestaurantMatchingNearbyTopRatedTwentyRestaurantStrategy topRatedTwentyRestaurantStrategy;

    public RestaurantMatchingStrategy restaurantMatchingStrategy(double rating) {
        if(rating >=4.8) {
            return topRatedTwentyRestaurantStrategy;
        }
        else {
            return nearestTwentyRestaurantStrategy;
        }
    }
}
