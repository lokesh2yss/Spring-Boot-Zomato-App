package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
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
public class OrderRequestDto {
    private Double price;

    private Map<List<MenuItemDto>, Integer> items;

    private CustomerDto customer;

    private RestaurantDto restaurant;

    private PaymentMethod paymentMethod;

    private AddressDto deliveryAddress;

    private OrderRequestStatus requestStatus;

    private LocalDateTime createdAt;
}
