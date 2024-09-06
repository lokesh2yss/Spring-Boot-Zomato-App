package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.enums.AccountStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.DeliveryExecutiveRepository;
import com.codingshuttle.app.zomatoApp.repositories.UserRepository;
import com.codingshuttle.app.zomatoApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final PaymentService paymentService;
    private final RatingService ratingService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderDto acceptOrderDelivery(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if(order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.NOT_ASSIGNED)) {
            throw new RuntimeException("Invalid order delivery status:"+order.getOrderDeliveryStatus());
        }
        if(order.getDeliveryExecutive() != null) {
            throw new RuntimeException("Order already assigned. Cannot be reassigned");
        }
        order.setOrderDeliveryStatus(OrderDeliveryStatus.DELIVERY_ASSIGNED);
        order.setDeliveryExecutive(getCurrentDeliveryExecutive());

        Order savedOrder = orderService.updateOrder(order);
        paymentService.createNewPayment(savedOrder);
        ratingService.createNewRating(savedOrder);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto pickupOrderForDelivery(Long orderId, String pickupOtp) {
        Order order = orderService.getOrderById(orderId);
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERY_ASSIGNED)) {
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

        return modelMapper.map(orderService.updateOrder(order), OrderDto.class);
    }

    @Override
    public OrderDto completeOrderDelivery(Long orderId, String deliveryOtp) {
        Order order = orderService.getOrderById(orderId);
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
        Order updatedOrder = orderService.updateOrder(order);
        paymentService.processPayment(updatedOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public LiveLocationResponseDto getDeliveryExecutiveLiveLocation(Order order) {
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERED) && !order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            throw new RuntimeException("Invalid order status, status:"+order.getOrderStatus());
        }
        PointDto pointDto = modelMapper.map(order.getDeliveryExecutive().getCurrentLocation(), PointDto.class);

        return LiveLocationResponseDto
                .builder()
                .liveLocation(pointDto)
                .orderId(order.getId())
                .deliveryExecutiveId(order.getDeliveryExecutive().getId())
                .build();
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

    @Override
    public Page<DeliveryExecutive> getAllDeliveryExecutives(PageRequest pageRequest) {
        return deliveryExecutiveRepository.findAll(pageRequest);
    }

    @Override
    public void banDeliveryExecutive(Long deliveryExecutiveId) {
        User user = getUserByDeliveryExecutiveId(deliveryExecutiveId);
        user.setAccountStatus(AccountStatus.BANNED);
        userRepository.save(user);
    }

    @Override
    public Page<OrderDto> getAllMyOrders(PageRequest pageRequest) {
        DeliveryExecutive deliveryExecutive = getCurrentDeliveryExecutive();
        return orderService.getAllOrdersOfDeliveryExecutive(deliveryExecutive, pageRequest)
                .map((element) -> modelMapper.map(element, OrderDto.class));
    }

    @Override
    public CustomerDto rateCustomer(Long deliveryExecutiveId, Long orderId, Integer rating) {
        DeliveryExecutive deliveryExecutive = getCurrentDeliveryExecutive();
        validateDeliveryExecutive(deliveryExecutive, deliveryExecutiveId);
        Order order = orderService.getOrderById(orderId);
        if(!order.getDeliveryExecutive().equals(deliveryExecutive)) {
            throw new RuntimeConflictException("The delivery executive cannot rate as the " +
                    "order doesn't belong to delivery executive with id: "+deliveryExecutive.getId());
        }
        if(!order.getOrderDeliveryStatus().equals(OrderDeliveryStatus.DELIVERED)) {
            throw new RuntimeConflictException("Order has not been delivered yet"+
                    ", so cannot start rating, status: "+order.getOrderDeliveryStatus());
        }
        return ratingService.rateCustomer(order, rating);
    }

    @Override
    public DeliveryExecutive update(DeliveryExecutive deliveryExecutive) {
        return deliveryExecutiveRepository.save(deliveryExecutive);
    }

    private User getUserByDeliveryExecutiveId(Long deliveryExecutiveId) {
        DeliveryExecutive deliveryExecutive = deliveryExecutiveRepository.findById(deliveryExecutiveId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Executive not found with id="+deliveryExecutiveId));

        return deliveryExecutive.getUser();
    }
    private DeliveryExecutive getCurrentDeliveryExecutive() {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return deliveryExecutiveRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery executive not associated with user with id: "+user.getId()));
    }

    private void validateDeliveryExecutive(DeliveryExecutive deliveryExecutive, Long deliveryExecutiveId) {
        if(!deliveryExecutive.getId().equals(deliveryExecutiveId)) {
            throw new RuntimeConflictException("The deliveryExecutive not logged in with id: "+deliveryExecutiveId);
        }
    }

}
