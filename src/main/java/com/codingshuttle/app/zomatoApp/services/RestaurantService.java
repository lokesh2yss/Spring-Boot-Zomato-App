package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RestaurantService {

    MenuItemDto addMenuItem(MenuItemDto menuItemDto);

    boolean deleteMenuItem(Long menuItemId);

    MenuItemDto updateMenuItem(Long menuItemId, MenuItemDto menuItemDto);

    MenuItemDto getMenuItemById(Long menuItemId);

    List<MenuItemDto> getRestaurantMenu(Long restaurantId);

    boolean cancelOrder(Long orderId);

    boolean acceptOrderCancellation(Long orderId);

    boolean rejectOrderCancellation(Long orderId);

    PointDto getDeliveryExecutiveLiveLocation(Long orderId);

    List<RestaurantDto> getNearbyRestaurantsByCustomer(Customer customer);

    AddressDto addRestaurantAddress(AddressDto addressDto);

    AddressDto updateRestaurantAddress(Long addressId, AddressDto addressDto);

    boolean deleteRestaurantAddress(Long addressId);

    AddressDto getRestaurantDefaultAddress();

    AddressDto setRestaurantDefaultAddress(Long addressId);

    Restaurant getRestaurantById(Long restaurantId);

    Restaurant createNewRestaurant(Restaurant restaurant);

    Page<Restaurant> getAllRestaurants(PageRequest pageRequest);

    void banRestaurant(Long restaurantId);

    Page<OrderDto> getAllMyOrders(PageRequest pageRequest);

}
