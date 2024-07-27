package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import org.locationtech.jts.geom.Point;

import java.awt.print.Pageable;
import java.util.List;

public interface RestaurantService {
    MenuItemDto addMenuItem(MenuItemDto menuItemDto);

    boolean deleteMenuItem(Long menuItemId);

    MenuItemDto updateMenuItem(MenuItemDto menuItemDto);

    boolean cancelOrder(Long orderId);

    boolean acceptOrderCancellation(Long orderId);

    boolean rejectOrderCancellation(Long orderId);

    Point getDeliveryExecutiveLiveLocation(Long orderId);

    List<OrderDto> getAllOrdersByCustomer(Long customerId);

    List<OrderDto> getOrderHistory(Pageable pageable);

    List<RestaurantDto> getNearbyRestaurantsByCustomer(Point customerLocation);

    List<MenuItemDto> getRestaurantMenu(Long restaurantId);

    AddressDto addRestaurantAddress(Long restaurantId, AddressDto addressDto);

    AddressDto updateRestaurantAddress(Long restaurantId, Long addressId, AddressDto addressDto);

    boolean deleteRestaurantAddress(Long restaurantId, Long addressId);

    AddressDto getRestaurantDefaultAddress(Long  restaurantId);

    AddressDto setRestaurantDefaultAddress(Long  restaurantId, Long addressId);
}
