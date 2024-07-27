package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/{customerId}/addresses")
    public AddressDto addAddress(
            @PathVariable("customerId") Long customerId,
            @RequestBody AddressDto addressDto) {
        return customerService.addCustomerAddress(customerId, addressDto);

    }
    @GetMapping("/{customerId}/default-address")
    public AddressDto getCustomerDefaultAddress(
            @PathVariable("customerId") Long customerId) {
        return customerService.getCustomerDefaultAddress(customerId);
    }

    @PostMapping("/{customerId}/default-address/{addressId}")
    public AddressDto setDefaultAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId) {
        return customerService.setCustomerDefaultAddress(customerId, addressId);
    }

    @PutMapping("/{customerId}/addresses/{addressId}")
    public AddressDto updateAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDto addressDto) {
        return customerService.updateCustomerAddress(customerId, addressId, addressDto);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public boolean deleteAddress(
            @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId) {
        return customerService.deleteCustomerAddress(customerId, addressId);
    }
}

