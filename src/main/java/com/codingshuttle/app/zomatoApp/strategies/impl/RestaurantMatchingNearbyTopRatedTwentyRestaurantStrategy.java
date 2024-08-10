package com.codingshuttle.app.zomatoApp.strategies.impl;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.repositories.RestaurantRepository;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import com.codingshuttle.app.zomatoApp.strategies.RestaurantMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMatchingNearbyTopRatedTwentyRestaurantStrategy implements RestaurantMatchingStrategy {
    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;
    private final ModelMapper modelMapper;
    @Override
    public List<Restaurant> findMatchingRestaurants(Customer customer) {
        PointDto locationDto = geoLocationService.getOpenCageGeolocation(customer.getUser().getDefaultAddress().toString());
        Point customerLocation = modelMapper.map(locationDto, Point.class);
        return restaurantRepository.findTwentyNearbyTopRatedRestaurants(customerLocation);
    }
}
