package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestItemDto {
    private Long menuItemId;
    private Integer quantity;
}
