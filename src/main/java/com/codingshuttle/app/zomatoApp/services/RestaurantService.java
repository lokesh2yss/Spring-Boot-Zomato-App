package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.*;
import org.locationtech.jts.geom.Point;

import java.awt.print.Pageable;
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

    List<RestaurantDto> getNearbyRestaurantsByCustomer(Point customerLocation);


    AddressDto addRestaurantAddress(Long restaurantId, AddressDto addressDto);

    AddressDto updateRestaurantAddress(Long restaurantId, Long addressId, AddressDto addressDto);

    boolean deleteRestaurantAddress(Long restaurantId, Long addressId);

    AddressDto getRestaurantDefaultAddress(Long  restaurantId);

    AddressDto setRestaurantDefaultAddress(Long  restaurantId, Long addressId);

}
