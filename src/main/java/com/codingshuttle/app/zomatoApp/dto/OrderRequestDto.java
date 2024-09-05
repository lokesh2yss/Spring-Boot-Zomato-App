package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long id;

    private BigDecimal totalPrice;

    private int totalItemCount;

    private List<OrderItemDto> orderItems;

    private CustomerDto customer;

    private RestaurantDto restaurant;

    private OrderRequestStatus requestStatus;

    private LocalDateTime createdAt;
}
