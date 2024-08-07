package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.DeliveryExecutiveRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import com.codingshuttle.app.zomatoApp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryExecutiveServiceImpl implements DeliveryExecutiveService {
    private final DeliveryExecutiveRepository deliveryExecutiveRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final OrderService orderService;

    @Override
    public PointDto getDeliveryExecutiveLiveLocation(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if(order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            throw new RuntimeException("Invalid order status, status:"+order.getOrderStatus());
        }

        return modelMapper.map(order.getDeliveryExecutive().getCurrentLocation(), PointDto.class);
    }

    @Override
    public AddressDto addDeliveryExecutiveAddress(Long deliveryExecutiveId, AddressDto addressDto) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        return addressService.addAddressForUser(user.getId(), addressDto);
    }

    @Override
    public AddressDto updateDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId, AddressDto addressDto) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        return addressService.updateAddressForUser(user.getId(), addressId, addressDto);
    }

    @Override
    public boolean deleteDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        return addressService.deleteAddressForUser(user.getId(), addressId);
    }

    @Override
    public AddressDto getDeliveryExecutiveDefaultAddress(Long deliveryExecutiveId) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        return modelMapper.map(user.getDefaultAddress(), AddressDto.class);
    }

    @Override
    public AddressDto setDeliveryExecutiveDefaultAddress(Long deliveryExecutiveId, Long addressId) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        return addressService.setDefaultAddressForUser(user.getId(), addressId);
    }
    private User getUserByDeliveryExecutiveId(Long deliveryExecutiveId) {
        DeliveryExecutive deliveryExecutive = deliveryExecutiveRepository.findById(deliveryExecutiveId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Executive not found with id="+deliveryExecutiveId));

        return deliveryExecutive.getUser();
    }
}
