package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.AccountStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.RestaurantRepository;
import com.codingshuttle.app.zomatoApp.repositories.UserRepository;
import com.codingshuttle.app.zomatoApp.services.*;
import com.codingshuttle.app.zomatoApp.strategies.RestaurantStrategyManager;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
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
    private final UserRepository userRepository;
    private final OrderService orderService;

    @Override
    public MenuItemDto addMenuItem(MenuItemDto menuItemDto) {
        Restaurant restaurant = getCurrentRestaurant();
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        menuItem.setRestaurant(restaurant);
        menuItem.setId(null);
        MenuItem savedMenuItem = menuItemService.addMenuItem(menuItem);

        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }

    @Override
    public boolean deleteMenuItem(Long menuItemId) {
        Restaurant restaurant = getCurrentRestaurant();
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot delete menu item as it doesn't belong to restaurant with id:"+restaurant.getId());
        }
        return menuItemService.deleteMenuItem(menuItemId);
    }

    @Override
    public MenuItemDto updateMenuItem(Long menuItemId,  MenuItemDto menuItemDto) {
        Restaurant restaurant = getCurrentRestaurant();
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot update menu item as it doesn't belong to restaurant with id:"+restaurant.getId());
        }
        MenuItem savedMenuItem = menuItemService.updateMenuItem(menuItemId, menuItem);
        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }

    @Override
    public MenuItemDto getMenuItemById(Long menuItemId) {
        Restaurant restaurant = getCurrentRestaurant();
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        if(!menuItem.getRestaurant().equals(restaurant)) {
            throw new RuntimeException("Cannot get the menu item as it doesn't belong to restaurant with id:"+restaurant.getId());
        }
        return modelMapper.map(menuItem, MenuItemDto.class);
    }

    @Override
    public List<MenuItemDto> getRestaurantMenu(Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant
                .getMenuItems()
                .stream().map((menuItem) -> modelMapper.map(menuItem, MenuItemDto.class))
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
    public AddressDto addRestaurantAddress(AddressDto addressDto) {
        Restaurant restaurant = getCurrentRestaurant();
        PointDto restaurantLocation = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        restaurant.setLocation(modelMapper.map(restaurantLocation, Point.class));
        User user = restaurant.getUser();
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateRestaurantAddress(Long addressId, AddressDto addressDto) {
        Restaurant restaurant = getCurrentRestaurant();
        PointDto restaurantLocation = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        restaurant.setLocation(modelMapper.map(restaurantLocation, Point.class));
        User user = restaurant.getUser();
        return addressService.updateAddressForUser(user.getId(), addressId, addressDto);
    }

    @Override
    public boolean deleteRestaurantAddress(Long addressId) {
        Restaurant restaurant = getCurrentRestaurant();
        User user = restaurant.getUser();
        return addressService.deleteAddressForUser(user.getId(), addressId);
    }

    @Override
    public AddressDto getRestaurantDefaultAddress() {
        Restaurant restaurant = getCurrentRestaurant();
        User user = restaurant.getUser();
        return modelMapper.map(user.getDefaultAddress(), AddressDto.class);
    }

    @Override
    public AddressDto setRestaurantDefaultAddress(Long addressId) {
        Restaurant restaurant = getCurrentRestaurant();
        User user = restaurant.getUser();
        return addressService.setDefaultAddressForUser(user.getId(), addressId);
    }
    private User getUserByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id="+restaurantId));

        return restaurant.getUser();
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found with id:"+restaurantId));
    }

    @Override
    public Restaurant createNewRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Page<Restaurant> getAllRestaurants(PageRequest pageRequest) {
        return restaurantRepository.findAll(pageRequest);
    }

    @Override
    public void banRestaurant(Long restaurantId) {
        User user  = getUserByRestaurantId(restaurantId);
        user.setAccountStatus(AccountStatus.BANNED);
        userRepository.save(user);
    }

    @Override
    public Page<OrderDto> getAllMyOrders(PageRequest pageRequest) {
        Restaurant restaurant = getCurrentRestaurant();
        return orderService.getAllOrdersOfRestaurant(restaurant, pageRequest)
                .map((element) -> modelMapper.map(element, OrderDto.class));
    }

    private Restaurant getCurrentRestaurant() {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return restaurantRepository.findByUser(user).orElseThrow(() ->
                new ResolutionException("Restaurant not associated with user with id "+user.getId()));
    }

}
