package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.CustomerDto;
import com.codingshuttle.app.zomatoApp.dto.DeliveryExecutiveDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.*;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.CustomerRepository;
import com.codingshuttle.app.zomatoApp.repositories.DeliveryExecutiveRepository;
import com.codingshuttle.app.zomatoApp.repositories.RatingRepository;
import com.codingshuttle.app.zomatoApp.repositories.RestaurantRepository;
import com.codingshuttle.app.zomatoApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final DeliveryExecutiveRepository deliveryExecutiveRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public DeliveryExecutiveDto rateDeliveryExecutive(Order order, Integer rating) {
        DeliveryExecutive deliveryExecutive = order.getDeliveryExecutive();
        Rating ratingObj = ratingRepository.findByOrder(order)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rating not found for order with id: "+order.getId()));
        if(ratingObj.getDeliveryExecutiveRating() != null) {
            throw new RuntimeConflictException("Delivery Executive already rated, cannot rate again");
        }

        ratingObj.setDeliveryExecutiveRating(rating);
        ratingRepository.save(ratingObj);
        Double newDeliveryExecutiveRating = ratingRepository
                .findByDeliveryExecutive(deliveryExecutive)
                .stream()
                .mapToDouble(Rating::getDeliveryExecutiveRating)
                .average()
                .orElse(0.0);
        deliveryExecutive.setRating(newDeliveryExecutiveRating);
        DeliveryExecutive updatedDeliveryExecutive = deliveryExecutiveRepository.save(deliveryExecutive);
        return modelMapper.map(updatedDeliveryExecutive, DeliveryExecutiveDto.class);
    }

    @Override
    public CustomerDto rateCustomer(Order order, Integer rating) {
        Customer customer = order.getCustomer();
        Rating ratingObj = ratingRepository.findByOrder(order)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rating not found for order with id: "+order.getId()));
        if(ratingObj.getCustomerRating() != null) {
            throw new RuntimeConflictException("Customer already rated, cannot rate again");
        }

        ratingObj.setCustomerRating(rating);
        ratingRepository.save(ratingObj);
        double newCustomerRating = ratingRepository.findByCustomer(customer)
                .stream()
                .mapToDouble(Rating::getCustomerRating)
                .average()
                .orElse(0.0);
        customer.setRating(newCustomerRating);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public RestaurantDto rateRestaurant(Order order, Integer rating) {
        Restaurant restaurant = order.getRestaurant();
        Rating ratingObj = ratingRepository.findByOrder(order)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rating not found for order with id: "+order.getId()));
        if(ratingObj.getRestaurantRating() != null) {
            throw new RuntimeConflictException("Restaurant already rated, cannot rate again");
        }

        ratingObj.setRestaurantRating(rating);
        ratingRepository.save(ratingObj);
        double newRestaurantRating = ratingRepository.findByRestaurant(restaurant)
                .stream()
                .mapToDouble(Rating::getRestaurantRating)
                .average()
                .orElse(0.0);
        restaurant.setRating(newRestaurantRating);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public void createNewRating(Order order) {
        Rating rating = Rating.builder()
                .order(order)
                .restaurant(order.getRestaurant())
                .deliveryExecutive(order.getDeliveryExecutive())
                .customer(order.getCustomer())
                .build();
        ratingRepository.save(rating);

    }
}
