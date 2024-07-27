package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.Role;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.UserRepository;
import com.codingshuttle.app.zomatoApp.services.AuthService;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import com.codingshuttle.app.zomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final DeliveryExecutiveService deliveryExecutiveService;
    @Override
    public String login(String email, String password) {
        return null;
    }

    @Override
    public UserDto signup(SignupDto signupDto) {
        Optional<User> userOptional = userRepository.findByEmail(signupDto.getEmail());
        if(userOptional.isPresent()) throw new RuntimeConflictException("Cannot sign up, user already exists by email: "+ signupDto.getEmail());
        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));

        User savedUser = userRepository.save(mappedUser);
        customerService.createNewCustomer(savedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public RestaurantDto onboardRestaurant(Long userId) {
        User user = getUserById(userId);
        user.setRoles(Set.of(Role.RESTAURANT));
        User savedUser = userRepository.save(user);
        Restaurant restaurant = new Restaurant();
        restaurant.setUser(savedUser);

        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    @Override
    public DeliveryExecutiveDto onboardDeliveryExecutive(Long userId) {
        User user = getUserById(userId);
        user.setRoles(Set.of(Role.DELIVERY_EXECUTIVE));
        User savedUser = userRepository.save(user);
        DeliveryExecutive deliveryExecutive = new DeliveryExecutive();
        deliveryExecutive.setUser(savedUser);

        return modelMapper.map(deliveryExecutive, DeliveryExecutiveDto.class);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return null;
    }

    @Override
    public List<DeliveryExecutiveDto> getAllDeliveryExecutive() {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return null;
    }

    @Override
    public boolean banDeliveryExecutive(Long deliveryExecutiveId) {
        return false;
    }

    @Override
    public boolean banRestaurant(Long restaurantId) {
        return false;
    }

    private User getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id="+userId));
    }
}
