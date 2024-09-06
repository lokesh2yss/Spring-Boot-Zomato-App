package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private OrderStatus orderStatus;
    private OrderDeliveryStatus orderDeliveryStatus;
}
