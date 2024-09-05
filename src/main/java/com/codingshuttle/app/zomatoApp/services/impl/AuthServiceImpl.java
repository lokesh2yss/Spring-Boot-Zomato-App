package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.Role;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.UserRepository;
import com.codingshuttle.app.zomatoApp.security.JwtService;
import com.codingshuttle.app.zomatoApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public String[] login(String email, String password) {
        Authentication authentication  = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[] {accessToken, refreshToken};
    }

    @Override
    public UserDto signup(SignupDto signupDto) {
        Optional<User> userOptional = userRepository.findByEmail(signupDto.getEmail());
        if(userOptional.isPresent()) throw new RuntimeConflictException("Cannot sign up, user already exists by email: "+ signupDto.getEmail());
        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));

        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);
        customerService.createNewCustomer(savedUser);

        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: "+userId));

        return jwtService.generateAccessToken(user);
    }

    @Override
    public RestaurantDto onboardNewRestaurant(Long userId, OnboardRestaurantDto onboardRestaurantDto) {
        User user = getUserById(userId);
        if(user.getRoles().contains(Role.RESTAURANT)) {
            throw new RuntimeConflictException("User with id: "+user.getId()+"already onboarded as a restaurant");
        }
        user.getRoles().add(Role.RESTAURANT);
        User savedUser = userRepository.save(user);
        Restaurant restaurant = modelMapper.map(onboardRestaurantDto, Restaurant.class);
        restaurant.setUser(savedUser);
        restaurant.setRating(0.0);
        Restaurant savedRestaurant = restaurantService.createNewRestaurant(restaurant);

        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public DeliveryExecutiveDto onboardNewDeliveryExecutive(Long userId, OnboardDeliveryExecutiveDto onboardDeliveryExecutiveDto) {
        User user = getUserById(userId);
        if(user.getRoles().contains(Role.DELIVERY_EXECUTIVE)) {
            throw new RuntimeConflictException("User with id: "+user.getId()+"already onboarded as a delivery executive");
        }
        user.getRoles().add(Role.DELIVERY_EXECUTIVE);
        User savedUser = userRepository.save(user);
        DeliveryExecutive deliveryExecutive = modelMapper.map(onboardDeliveryExecutiveDto, DeliveryExecutive.class);
        deliveryExecutive.setUser(savedUser);
        deliveryExecutive.setRating(0.0);
        DeliveryExecutive savedDeliveryExecutive = deliveryExecutiveService.createNewDeliveryExecutive(deliveryExecutive);

        return modelMapper.map(savedDeliveryExecutive, DeliveryExecutiveDto.class);
    }

    @Override
    public Page<RestaurantDto> getAllRestaurants(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<DeliveryExecutiveDto> getAllDeliveryExecutive(PageRequest pageRequest) {
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
