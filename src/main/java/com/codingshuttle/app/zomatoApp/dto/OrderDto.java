package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Double price;

    private Map<List<MenuItemDto>, Integer> items;

    private CustomerDto customer;

    private RestaurantDto restaurant;

    private DeliveryExecutiveDto deliveryExecutive;

    private PaymentMethod paymentMethod;

    private AddressDto deliveryAddress;

    private OrderStatus orderStatus;

    private String pickupOtp;

    private String deliveryOtp;

    private LocalDateTime placedAt;

    private LocalDateTime pickedAt;

    private LocalDateTime deliveredAt;
}
