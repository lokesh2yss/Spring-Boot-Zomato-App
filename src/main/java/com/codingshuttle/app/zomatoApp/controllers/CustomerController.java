package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/{customerId}/orders/")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable Long customerId, @RequestBody ConfirmOrderDto confirmOrderDto) {
        return ResponseEntity.ok(customerService.placeOrder(customerId, confirmOrderDto));
    }
    @DeleteMapping(path = "/{customerId}/order-requests/{menuItemId}")
    public ResponseEntity<OrderRequestDto> deleteOrderItemFromOrderRequest(@PathVariable Long customerId, @PathVariable Long menuItemId) {
        return ResponseEntity.ok(customerService.deleteOrderItemFromOrderRequest(customerId, menuItemId));
    }

    @PostMapping(path = "/{customerId}/order-requests")
    public ResponseEntity<OrderRequestDto> addOrderItemToOrderRequest(@PathVariable Long customerId, @RequestBody OrderRequestItemDto orderRequestItemDto) {
        return ResponseEntity.ok(customerService.addOrderItemToOrderRequest(customerId, orderRequestItemDto));
    }

    @PostMapping("/{customerId}/orders/{orderId}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(
            @PathVariable("customerId") Long customerId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(customerService.cancelOrder(orderId));
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

