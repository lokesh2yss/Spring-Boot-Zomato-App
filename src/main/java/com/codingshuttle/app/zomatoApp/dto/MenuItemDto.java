package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.MenuItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {
    private Double price;

    private String title;

    private RestaurantDto restaurant;

    private String description;

    private Double rating;

    private MenuItemStatus status;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
