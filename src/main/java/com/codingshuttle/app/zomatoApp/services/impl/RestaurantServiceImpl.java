package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.RestaurantRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
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

    @Override
    public AddressDto addRestaurantAddress(Long restaurantId, AddressDto addressDto) {
        User user = getUserByRestaurantId(restaurantId);
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateRestaurantAddress(Long restaurantId, Long addressId, AddressDto addressDto) {
        User user = getUserByRestaurantId(restaurantId);
        return addressService.updateAddressForUser(user.getId(), addressId, addressDto);
    }

    @Override
    public boolean deleteRestaurantAddress(Long restaurantId, Long addressId) {
        User user = getUserByRestaurantId(restaurantId);
        return addressService.deleteAddressForUser(user.getId(), addressId);
    }

    @Override
    public AddressDto getRestaurantDefaultAddress(Long restaurantId) {
        User user = getUserByRestaurantId(restaurantId);
        return modelMapper.map(user.getDefaultAddress(), AddressDto.class);
    }

    @Override
    public AddressDto setRestaurantDefaultAddress(Long restaurantId, Long addressId) {
        User user = getUserByRestaurantId(restaurantId);
        return addressService.setDefaultAddressForUser(user.getId(), addressId);
    }
    private User getUserByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id="+restaurantId));

        return restaurant.getUser();
    }
}
