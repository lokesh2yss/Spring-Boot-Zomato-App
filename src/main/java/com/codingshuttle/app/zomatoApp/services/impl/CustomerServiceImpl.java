package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.repositories.CustomerRepository;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<RestaurantDto> getNearbyRestaurantsByCustomer(CustomerDto customerDto) {
        return null;
    }

    @Override
    public List<MenuItemDto> getRestaurantMenu(Long restaurantId) {
        return null;
    }

    @Override
    public MenuItemDto addMenuItemToOrderRequest(OrderRequestDto orderRequestDto, MenuItemDto menuItemDto) {
        return null;
    }

    @Override
    public MenuItemDto deleteMenuItemFromOrderRequest(OrderRequestDto orderRequestDto, MenuItemDto menuItemDto) {
        return null;
    }

    @Override
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public OrderDto cancelOrder(Long orderId) {
        return null;
    }

    @Override
    public AddressDto addCustomerAddress(AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto deleteCustomerAddress(Long addressId) {
        return null;
    }

    @Override
    public List<OrderDto> getCustomersAllCurrentOrders(Long customerId) {
        return null;
    }

    @Override
    public List<OrderDto> getCustomerOrderHistory(Long customerId) {
        return null;
    }

    @Override
    public boolean addFundsToWallet(Long customerId, double amount) {
        return false;
    }

    @Override
    public AddressDto getDefaultAddress(Long customerId) {
        return null;
    }

    @Override
    public AddressDto setDefaultAddress(Long customerId, AddressDto addressDto) {
        return null;
    }

    @Override
    public OrderStatus getCustomerOrderStatus(Long orderId) {
        return null;
    }

    @Override
    public Point getDeliveryExecutiveLiveLocation(Long orderId) {
        return null;
    }

    @Override
    public boolean tipDeliveryExecutive(Long orderId, double amount) {
        return false;
    }

    @Override
    public boolean rateDeliveryExecutive(Long orderId, Integer rating) {
        return false;
    }

    @Override
    public boolean rateRestaurant(Long orderId, Integer rating) {
        return false;
    }

    @Override
    public boolean rateMenuItem(Long menuItemId, Integer rating) {
        return false;
    }

    @Override
    public ReviewDto writeRestaurantReview(ReviewDto reviewDto) {
        return null;
    }

    @Override
    public void createNewCustomer(User user) {
        Customer toSaveCustomer = Customer.builder()
                        .user(user)
                        .rating(0.0)
                        .build();
        customerRepository.save(toSaveCustomer);

    }
}
