package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
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

    OrderDto cancelOrder(Long orderId);

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
