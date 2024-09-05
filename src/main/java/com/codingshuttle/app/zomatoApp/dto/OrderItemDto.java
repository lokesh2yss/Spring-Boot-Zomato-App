package com.codingshuttle.app.zomatoApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    @JsonIgnore
    private OrderDto order;

    @JsonIgnore
    private OrderRequestDto orderRequest;

    private MenuItemDto menuItem;

    private Integer quantity;
}
