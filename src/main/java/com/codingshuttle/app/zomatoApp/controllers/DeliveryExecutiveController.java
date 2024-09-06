package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.OrderCompleteDto;
import com.codingshuttle.app.zomatoApp.dto.OrderDto;
import com.codingshuttle.app.zomatoApp.dto.OrderPickupDto;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-executives")
@RequiredArgsConstructor
public class DeliveryExecutiveController {

    private final DeliveryExecutiveService deliveryExecutiveService;

    @PostMapping(path = "/{deliveryExecutiveId}/orders/{orderId}/accept")
    public ResponseEntity<OrderDto> acceptOrderDelivery(@PathVariable Long deliveryExecutiveId,
                                                        @PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryExecutiveService.acceptOrderDelivery(orderId));
    }

    @PostMapping(path = "/{deliveryExecutiveId}/orders/{orderId}/pickup")
    public ResponseEntity<OrderDto> pickOrderForDelivery(@PathVariable Long deliveryExecutiveId,
                                                         @PathVariable Long orderId,
                                                         @RequestBody OrderPickupDto pickupDto) {
        return ResponseEntity.ok(deliveryExecutiveService.pickupOrderForDelivery(orderId, pickupDto.getPickupOtp()));
    }

    @PostMapping(path = "/{deliveryExecutiveId}/orders/{orderId}/complete")
    public ResponseEntity<OrderDto> completeOrderDelivery(@PathVariable Long deliveryExecutiveId,
                                                          @PathVariable Long orderId,
                                                          @RequestBody OrderCompleteDto orderCompleteDto) {
        return ResponseEntity.ok(deliveryExecutiveService.completeOrderDelivery(orderId, orderCompleteDto.getDeliveryOtp()));
    }

    @PostMapping("/{deliveryExecutiveId}/addresses")
    public ResponseEntity<AddressDto> addAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(deliveryExecutiveService.addDeliveryExecutiveAddress(deliveryExecutiveId, addressDto), HttpStatus.CREATED);
    }

    @GetMapping("/{deliveryExecutiveId}/default-address")
    public ResponseEntity<AddressDto> getDeliveryExecutiveDefaultAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId) {
        return ResponseEntity.ok(deliveryExecutiveService.getDeliveryExecutiveDefaultAddress(deliveryExecutiveId));
    }

    @PostMapping("/{deliveryExecutiveId}/default-address/{addressId}")
    public ResponseEntity<AddressDto> setDefaultAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(deliveryExecutiveService.setDeliveryExecutiveDefaultAddress(deliveryExecutiveId, addressId), HttpStatus.CREATED);
    }

    @PutMapping("/{deliveryExecutiveId}/addresses/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(deliveryExecutiveService.updateDeliveryExecutiveAddress(deliveryExecutiveId, addressId, addressDto));
    }

    @DeleteMapping("/{deliveryExecutiveId}/addresses/{addressId}")
    public ResponseEntity<Boolean> deleteAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok(deliveryExecutiveService.deleteDeliveryExecutiveAddress(deliveryExecutiveId, addressId));
    }
}


