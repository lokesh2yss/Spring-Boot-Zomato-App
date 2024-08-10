package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;

import java.util.List;

public interface RestaurantService {

    MenuItemDto addMenuItem(Long restaurantId, MenuItemDto menuItemDto);

    boolean deleteMenuItem(Long restaurantId, Long menuItemId);

    MenuItemDto updateMenuItem(Long restaurantId, Long menuItemId, MenuItemDto menuItemDto);

    MenuItemDto getMenuItemById(Long restaurantId, Long menuItemId);

    List<MenuItemDto> getRestaurantMenu(Long restaurantId);

    boolean cancelOrder(Long orderId);

    boolean acceptOrderCancellation(Long orderId);

    boolean rejectOrderCancellation(Long orderId);

    PointDto getDeliveryExecutiveLiveLocation(Long orderId);

    List<RestaurantDto> getNearbyRestaurantsByCustomer(Customer customer);

    AddressDto addRestaurantAddress(Long restaurantId, AddressDto addressDto);

    AddressDto updateRestaurantAddress(Long restaurantId, Long addressId, AddressDto addressDto);

    boolean deleteRestaurantAddress(Long restaurantId, Long addressId);

    AddressDto getRestaurantDefaultAddress(Long  restaurantId);

    AddressDto setRestaurantDefaultAddress(Long  restaurantId, Long addressId);

    Restaurant getRestaurantById(Long restaurantId);
}
