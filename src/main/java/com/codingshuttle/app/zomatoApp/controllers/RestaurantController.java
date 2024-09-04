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
        return restaurantService.addMenuItem(menuItemDto);
    }

    @PutMapping("/{restaurantId}/menu-items/{menuItemId}")
    public MenuItemDto updateMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId,
            @RequestBody MenuItemDto menuItemDto) {
        return restaurantService.updateMenuItem(menuItemId, menuItemDto);
    }

    @DeleteMapping("/{restaurantId}/menu-items/{menuItemId}")
    public boolean deleteMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return restaurantService.deleteMenuItem(menuItemId);
    }

    @GetMapping("/{restaurantId}/menu-items/{menuItemId}")
    public MenuItemDto getMenuItemById(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return restaurantService.getMenuItemById(menuItemId);
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
        return restaurantService.addRestaurantAddress(addressDto);
    }

    @GetMapping("/{restaurantId}/default-address")
    public AddressDto getRestaurantDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId) {
        return restaurantService.getRestaurantDefaultAddress();
    }

    @PostMapping("/{restaurantId}/default-address/{addressId}")
    public AddressDto setDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return restaurantService.setRestaurantDefaultAddress(addressId);
    }

    @PutMapping("/{restaurantId}/addresses/{addressId}")
    public AddressDto updateAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return restaurantService.updateRestaurantAddress(addressId, addressDto);
    }

    @DeleteMapping("/{restaurantId}/addresses/{addressId}")
    public boolean deleteAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return restaurantService.deleteRestaurantAddress(addressId);
    }
}
