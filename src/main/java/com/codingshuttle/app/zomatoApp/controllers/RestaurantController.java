package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/{restaurantId}/menu-items")
    public MenuItemDto addMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody MenuItemDto menuItemDto) {
        return restaurantService.addMenuItem(restaurantId, menuItemDto);
    }

    @PutMapping("/{restaurantId}/menu-items/{menuItemId}")
    public MenuItemDto updateMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId,
            @RequestBody MenuItemDto menuItemDto) {
        return restaurantService.updateMenuItem(restaurantId, menuItemId, menuItemDto);
    }

    @DeleteMapping("/{restaurantId}/menu-items/{menuItemId}")
    public boolean deleteMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return restaurantService.deleteMenuItem(restaurantId, menuItemId);
    }

    @GetMapping("/{restaurantId}/menu-items/{menuItemId}")
    public MenuItemDto getMenuItemById(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return restaurantService.getMenuItemById(restaurantId, menuItemId);
    }

    @GetMapping("/{restaurantId}/menu-items")
    public List<MenuItemDto> getAllMenuItemsByRestaurant(
            @PathVariable("restaurantId") Long restaurantId) {
        return restaurantService.getRestaurantMenu(restaurantId);
    }

    @PostMapping("/{restaurantId}/addresses")
    public AddressDto addAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody AddressDto addressDto) {
        return restaurantService.addRestaurantAddress(restaurantId, addressDto);
    }

    @GetMapping("/{restaurantId}/default-address")
    public AddressDto getRestaurantDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId) {
        return restaurantService.getRestaurantDefaultAddress(restaurantId);
    }

    @PostMapping("/{restaurantId}/default-address/{addressId}")
    public AddressDto setDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return restaurantService.setRestaurantDefaultAddress(restaurantId, addressId);
    }

    @PutMapping("/{restaurantId}/addresses/{addressId}")
    public AddressDto updateAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return restaurantService.updateRestaurantAddress(restaurantId, addressId, addressDto);
    }

    @DeleteMapping("/{restaurantId}/addresses/{addressId}")
    public boolean deleteAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return restaurantService.deleteRestaurantAddress(restaurantId, addressId);
    }
}
