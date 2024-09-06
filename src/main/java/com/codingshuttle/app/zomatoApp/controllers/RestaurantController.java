package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    @PostMapping("/{restaurantId}/orders/{orderId}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(restaurantService.cancelOrder(orderId));
    }
    @PostMapping("/{restaurantId}/menu-items")
    public ResponseEntity<MenuItemDto> addMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody MenuItemDto menuItemDto) {
        return new ResponseEntity<>(restaurantService.addMenuItem(menuItemDto), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}/menu-items/{menuItemId}")
    public ResponseEntity<MenuItemDto> updateMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId,
            @RequestBody MenuItemDto menuItemDto) {
        return ResponseEntity.ok(restaurantService.updateMenuItem(menuItemId, menuItemDto));
    }

    @DeleteMapping("/{restaurantId}/menu-items/{menuItemId}")
    public ResponseEntity<Boolean> deleteMenuItem(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return ResponseEntity.ok(restaurantService.deleteMenuItem(menuItemId));
    }

    @GetMapping("/{restaurantId}/menu-items/{menuItemId}")
    public ResponseEntity<MenuItemDto> getMenuItemById(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("menuItemId") Long menuItemId) {
        return ResponseEntity.ok(restaurantService.getMenuItemById(menuItemId));
    }

    @GetMapping("/{restaurantId}/menu-items")
    public ResponseEntity<List<MenuItemDto>> getAllMenuItemsByRestaurant(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantMenu(restaurantId));
    }

    @PostMapping("/{restaurantId}/addresses")
    public ResponseEntity<AddressDto> addAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(restaurantService.addRestaurantAddress(addressDto), HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}/default-address")
    public ResponseEntity<AddressDto> getRestaurantDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantDefaultAddress());
    }

    @PostMapping("/{restaurantId}/default-address/{addressId}")
    public ResponseEntity<AddressDto> setDefaultAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(restaurantService.setRestaurantDefaultAddress(addressId), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}/addresses/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurantAddress(addressId, addressDto));
    }

    @DeleteMapping("/{restaurantId}/addresses/{addressId}")
    public ResponseEntity<Boolean> deleteAddress(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(restaurantService.deleteRestaurantAddress(addressId));
    }
}
