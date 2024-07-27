package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private AddressService addressService;

    @PostMapping("/{customerId}/addresses")
    public AddressDto addAddress(
            @PathVariable("customerId") Long customerId,
            @RequestBody AddressDto addressDto) {
        AddressDto savedAddressDto = addressService.addAddressForUser(customerId, addressDto);
        return addressDto;
    }

    @PostMapping("/{customerId}/default-address/{addressId}")
    public AddressDto setDefaultAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId) {
        return addressService.setDefaultAddressForUser(customerId, addressId);
    }
}

