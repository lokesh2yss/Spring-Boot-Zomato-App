package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantCategory;
import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantCuisine;
import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantOpenStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private Long id;

    private Double rating;

    private UserDto user;

    @JsonIgnore
    private List<MenuItemDto> menuItems;

    private Time openingTime;

    private Time closingTime;

    private RestaurantOpenStatus openStatus;

    private RestaurantCategory category;

    private RestaurantCuisine cuisine;
}
