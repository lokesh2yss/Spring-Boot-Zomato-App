package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.entities.Address;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.AddressRepository;
import com.codingshuttle.app.zomatoApp.repositories.UserRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final GeoLocationService geoLocationService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Override
    public AddressDto addAddressForUser(Long userId, AddressDto addressDto) {
        User user = getUserById(userId);
        PointDto pointDto = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        addressDto.setLocation(pointDto);
        Address address = modelMapper.map(addressDto, Address.class);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        user.getAddresses().add(savedAddress);
        if(user.getAddresses().size()==1) { //if this is the only address of the user, make it default as well
            user.setDefaultAddress(address);
        }
        userRepository.save(user);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public AddressDto setDefaultAddressForUser(Long userId, Long addressId) {
        User user = getUserById(userId);
        Address address = getAddressById(addressId);
        if(!userId.equals(address.getUser().getId())) throw new RuntimeConflictException("Address with id="+addressId+ " doesn't belong to the user with userId="+userId);

        user.setDefaultAddress(address);
        userRepository.save(user);
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public boolean deleteAddressForUser(Long userId, Long addressId) {
        User user = getUserById(userId);
        Address address = getAddressById(addressId);

        if(!userId.equals(address.getUser().getId())) throw new RuntimeConflictException("Address with id="+addressId+ " doesn't belong to the user with userId="+userId);
        if(user.getDefaultAddress().getId().equals(addressId)) {
            user.setDefaultAddress(null);
        }
        addressRepository.deleteById(addressId);
        user.getAddresses().remove(address);
        userRepository.save(user);
        return true;
    }

    @Override
    public AddressDto updateAddressForUser(Long userId, Long addressId, AddressDto addressDto) {
        User user = getUserById(userId);
        Address address = getAddressById(addressId);
        PointDto pointDto = geoLocationService.getOpenCageGeolocation(addressDto.toString());
        addressDto.setLocation(pointDto);

        if(!userId.equals(address.getUser().getId())) throw new RuntimeConflictException("Address with id="+addressId+ " doesn't belong to the user with userId="+userId);

        user.getAddresses().remove(address);
        modelMapper.map(addressDto, address);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        user.getAddresses().add(savedAddress);
        return modelMapper.map(savedAddress, AddressDto.class);
    }
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id="+userId));

    }
    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id="+addressId));

    }
}
