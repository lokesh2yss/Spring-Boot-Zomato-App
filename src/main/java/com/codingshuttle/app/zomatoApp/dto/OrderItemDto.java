package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    private OrderDto order;

    private OrderRequestDto orderRequest;

    private MenuItemDto menuItem;

    private Integer quantity;
}
