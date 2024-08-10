package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.dto.RestaurantDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.RestaurantRepository;
import com.codingshuttle.app.zomatoApp.services.*;
import com.codingshuttle.app.zomatoApp.strategies.RestaurantStrategyManager;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final MenuItemService menuItemService;
    private final DeliveryExecutiveService deliveryExecutiveService;
    private final GeoLocationService geoLocationService;
    private final RestaurantStrategyManager restaurantStrategyManager;
    @Override
    public MenuItemDto addMenuItem(Long restaurantId, MenuItemDto menuItemDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        menuItem.setRestaurant(restaurant);
        MenuItem savedMenuItem = menuItemService.addMenuItem(menuItem);

        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }

    @Override
    public boolean deleteMenuItem(Long restaurantId, Long menuItemId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot delete menu item as it doesn't belong to restaurant with id:"+restaurantId);
        }
        return menuItemService.deleteMenuItem(menuItemId);
    }

    @Override
    public MenuItemDto updateMenuItem(Long restaurantId, Long menuItemId,  MenuItemDto menuItemDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot update menu item as it doesn't belong to restaurant with id:"+restaurantId);
        }
        MenuItem savedMenuItem = menuItemService.updateMenuItem(menuItemId, menuItem);
        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }

    @Override
    public MenuItemDto getMenuItemById(Long restaurantId, Long menuItemId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot get the menu item as it doesn't belong to restaurant with id:"+restaurantId);
        }
        return modelMapper.map(menuItem, MenuItemDto.class);
    }

    @Override
    public List<MenuItemDto> getRestaurantMenu(Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant
                .getMenuItems()
                .stream().map((element) -> modelMapper.map(element, MenuItemDto.class))
                .collect(Collectors.toList());
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
    public PointDto getDeliveryExecutiveLiveLocation(Long orderId) {
        return deliveryExecutiveService.getDeliveryExecutiveLiveLocation(orderId);
    }

    @Override
    public List<RestaurantDto> getNearbyRestaurantsByCustomer(Customer customer) {
        return restaurantStrategyManager.restaurantMatchingStrategy(customer.getRating()).findMatchingRestaurants(customer)
                .stream().map((restaurant) -> modelMapper.map(restaurant, RestaurantDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public AddressDto addRestaurantAddress(Long restaurantId, AddressDto addressDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        PointDto restaurantLocation = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        restaurant.setLocation(modelMapper.map(restaurantLocation, Point.class));
        User user = getUserByRestaurantId(restaurantId);
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateRestaurantAddress(Long restaurantId, Long addressId, AddressDto addressDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        PointDto restaurantLocation = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        restaurant.setLocation(modelMapper.map(restaurantLocation, Point.class));
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

    private Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found with id:"+restaurantId));
    }
}
