package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.DeliveryExecutiveRepository;
import com.codingshuttle.app.zomatoApp.services.AddressService;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import com.codingshuttle.app.zomatoApp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryExecutiveServiceImpl implements DeliveryExecutiveService {
    private final DeliveryExecutiveRepository deliveryExecutiveRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final OrderService orderService;

    @Override
    @Transactional
    public Order acceptOrderDelivery(Order order) {
        if(order.getOrderDeliveryStatus() != null) {
            throw new RuntimeException("Invalid order delivery status:"+order.getOrderDeliveryStatus());
        }
        if(order.getDeliveryExecutive() != null) {
            throw new RuntimeException("Order already assigned. Cannot be reassigned");
        }
        order.setOrderDeliveryStatus(OrderDeliveryStatus.DELIVERY_ASSIGNED);
        order.setDeliveryExecutive(getCurrentDeliveryExecutive());
        return orderService.updateOrder(order);
    }

    @Override
    public Order pickupOrderForDelivery(Order order, String pickupOtp) {
        if(order.getOrderDeliveryStatus() != null) {
            throw new RuntimeException("Order delivery status is invalid:"+order.getOrderDeliveryStatus());
        }
        DeliveryExecutive deliveryExecutive = getCurrentDeliveryExecutive();
        if(!order.getDeliveryExecutive().equals(deliveryExecutive)) {
            throw new RuntimeException("Delivery Executive who was assigned to this order can only pickup the order");
        }
        if(!pickupOtp.equals(order.getPickupOtp())) {
            throw new RuntimeConflictException("Incorrect otp provided: "+pickupOtp);
        }

        order.setOrderDeliveryStatus(OrderDeliveryStatus.PICKED_UP);
        order.setPickedAt(LocalDateTime.now());

        return orderService.updateOrder(order);
    }

    @Override
    public Order completeOrderDelivery(Order order, String deliveryOtp) {
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.PICKED_UP)) {
            throw new RuntimeException("Order delivery status is invalid:"+order.getOrderDeliveryStatus());
        }
        DeliveryExecutive deliveryExecutive = getCurrentDeliveryExecutive();
        if(!order.getDeliveryExecutive().equals(deliveryExecutive)) {
            throw new RuntimeException("Delivery Executive who picked up the order can only complete the order");
        }
        if(!deliveryOtp.equals(order.getDeliveryOtp())) {
            throw new RuntimeConflictException("Incorrect otp provided: "+deliveryOtp);
        }
        order.setOrderDeliveryStatus(OrderDeliveryStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        return orderService.updateOrder(order);
    }

    @Override
    public PointDto getDeliveryExecutiveLiveLocation(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERED) && !order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
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

    @Override
    public DeliveryExecutive createNewDeliveryExecutive(DeliveryExecutive deliveryExecutive) {
        return deliveryExecutiveRepository.save(deliveryExecutive);
    }

    private User getUserByDeliveryExecutiveId(Long deliveryExecutiveId) {
        DeliveryExecutive deliveryExecutive = deliveryExecutiveRepository.findById(deliveryExecutiveId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Executive not found with id="+deliveryExecutiveId));

        return deliveryExecutive.getUser();
    }
    private DeliveryExecutive getCurrentDeliveryExecutive() {
        return deliveryExecutiveRepository.findById(3L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery executive not found with id:"+3));
    }
}
