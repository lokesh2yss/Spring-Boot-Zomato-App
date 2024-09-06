package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping(path = "/{customerId}/orders/current")
    public ResponseEntity<Page<OrderDto>> getAllCurrentOrdersOfCustomer(@PathVariable Long customerId,
                                                                           @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                                           @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(customerService.getCustomersAllCurrentOrders(customerId, pageRequest));
    }
    @GetMapping(path = "/{customerId}/restaurants/nearby")
    public ResponseEntity<List<RestaurantDto>> getNearbyRestaurants(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getNearbyRestaurants(customerId));
    }

    @PostMapping(path = "/{customerId}/order-requests")
    public ResponseEntity<OrderRequestDto> addOrderItemToOrderRequest(@PathVariable Long customerId, @RequestBody OrderRequestItemDto orderRequestItemDto) {
        return ResponseEntity.ok(customerService.addOrderItemToOrderRequest(customerId, orderRequestItemDto));
    }
    @DeleteMapping(path = "/{customerId}/order-requests/{menuItemId}")
    public ResponseEntity<OrderRequestDto> deleteOrderItemFromOrderRequest(@PathVariable Long customerId, @PathVariable Long menuItemId) {
        return ResponseEntity.ok(customerService.deleteOrderItemFromOrderRequest(customerId, menuItemId));
    }

    @PostMapping(path = "/{customerId}/orders/")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable Long customerId, @RequestBody ConfirmOrderDto confirmOrderDto) {
        return ResponseEntity.ok(customerService.placeOrder(customerId, confirmOrderDto));
    }

    @PostMapping("/{customerId}/orders/{orderId}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(
            @PathVariable("customerId") Long customerId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(customerService.cancelOrder(customerId, orderId));
    }

    @GetMapping(path = "/{customerId}/orders/{orderId}/getDeliveryStatus")
    public ResponseEntity<OrderStatusDto> getOrderStatus(@PathVariable Long customerId,
                                                         @PathVariable Long orderId) {
        return ResponseEntity.ok(customerService.getCustomerOrderStatus(customerId, orderId));
    }

    @PostMapping(path = "/{customerId}/orders/{orderId}/tipDeliveryExecutive")
    public ResponseEntity<Boolean> tipDeliveryExecutive(@PathVariable Long customerId,
                                                         @PathVariable Long orderId,
                                                               @RequestBody TipDto tipDto) {
        return ResponseEntity.ok(customerService.tipDeliveryExecutive(customerId, orderId, tipDto.getTipAmount()));
    }

    @PostMapping(path = "/{customerId}/orders/{orderId}/rateDeliveryExecutive")
    public ResponseEntity<DeliveryExecutiveDto> rateDeliveryExecutive(@PathVariable Long customerId,
                                                        @PathVariable Long orderId,
                                                        @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(customerService.rateDeliveryExecutive(customerId, orderId, ratingDto.getRating()));
    }

    @PostMapping(path = "/{customerId}/orders/{orderId}/rateRestaurant")
    public ResponseEntity<RestaurantDto> rateRestaurant(@PathVariable Long customerId,
                                                                     @PathVariable Long orderId,
                                                                     @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(customerService.rateRestaurant(customerId, orderId, ratingDto.getRating()));
    }

    @PostMapping(path = "/{customerId}/orders")
    public ResponseEntity<Page<OrderDto>> getAllMyOrders(@PathVariable Long customerId,
                                                        @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(customerService.getAllMyOrders(pageRequest));
    }
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<AddressDto> addAddress(
            @PathVariable("customerId") Long customerId,
            @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(customerService.addCustomerAddress(customerId, addressDto), HttpStatus.CREATED);

    }
    @GetMapping("/{customerId}/default-address")
    public ResponseEntity<AddressDto> getCustomerDefaultAddress(
            @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerDefaultAddress(customerId));
    }

    @PostMapping("/{customerId}/default-address/{addressId}")
    public ResponseEntity<AddressDto> setDefaultAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(customerService.setCustomerDefaultAddress(customerId, addressId), HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(customerService.updateCustomerAddress(customerId, addressId, addressDto));
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Boolean> deleteAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(customerService.deleteCustomerAddress(customerId, addressId), HttpStatus.OK);
    }
}

