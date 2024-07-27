package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-executives")
@RequiredArgsConstructor
public class DeliveryExecutiveController {

    private final DeliveryExecutiveService deliveryExecutiveService;

    @PostMapping("/{deliveryExecutiveId}/addresses")
    public AddressDto addAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @RequestBody AddressDto addressDto) {
        return deliveryExecutiveService.addDeliveryExecutiveAddress(deliveryExecutiveId, addressDto);
    }

    @GetMapping("/{deliveryExecutiveId}/default-address")
    public AddressDto getDeliveryExecutiveDefaultAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId) {
        return deliveryExecutiveService.getDeliveryExecutiveDefaultAddress(deliveryExecutiveId);
    }

    @PostMapping("/{deliveryExecutiveId}/default-address/{addressId}")
    public AddressDto setDefaultAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId) {
        return deliveryExecutiveService.setDeliveryExecutiveDefaultAddress(deliveryExecutiveId, addressId);
    }

    @PutMapping("/{deliveryExecutiveId}/addresses/{addressId}")
    public AddressDto updateAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return deliveryExecutiveService.updateDeliveryExecutiveAddress(deliveryExecutiveId, addressId, addressDto);
    }

    @DeleteMapping("/{deliveryExecutiveId}/addresses/{addressId}")
    public boolean deleteAddress(
            @PathVariable("deliveryExecutiveId") Long deliveryExecutiveId,
            @PathVariable("addressId") Long addressId) {
        return deliveryExecutiveService.deleteDeliveryExecutiveAddress(deliveryExecutiveId, addressId);
    }
}


