package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public MenuItemDto addMenuItem(MenuItemDto menuItemDto) {
        return null;
    }

    @Override
    public boolean deleteMenuItem(Long menuItemId) {
        return false;
    }

    @Override
    public MenuItemDto updateMenuItem(MenuItemDto menuItemDto) {
        return null;
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        return false;
    }

    @Override
    public boolean acceptOrderCancellation(Long orderId) {
        return false;
    }

    @Override
    public boolean rejectOrderCancellation(Long orderId) {
        return false;
    }

    @Override
    public Point getDeliveryExecutiveLiveLocation(Long orderId) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrdersByCustomer(Long customerId) {
        return null;
    }

    @Override
    public List<OrderDto> getOrderHistory(Pageable pageable) {
        return null;
    }

    @Override
    public List<RestaurantDto> getNearbyRestaurantsByCustomer(Point customerLocation) {
        return null;
    }

    @Override
    public List<MenuItemDto> getRestaurantMenu(Long restaurantId) {
        return null;
    }
}
