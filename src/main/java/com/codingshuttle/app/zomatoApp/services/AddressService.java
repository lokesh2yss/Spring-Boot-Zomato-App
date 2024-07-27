package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;

public interface AddressService {
    AddressDto addAddressForUser(Long userId, AddressDto addressDto);

    AddressDto setDefaultAddressForUser(Long userId, Long addressId);

    boolean deleteAddressForUser(Long userId, Long addressId);

    AddressDto updateAddressForUser(Long userId, Long addressId, AddressDto addressDto);
}
