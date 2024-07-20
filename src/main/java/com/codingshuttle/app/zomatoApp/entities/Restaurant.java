package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantCategory;
import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantCuisine;
import com.codingshuttle.app.zomatoApp.entities.enums.RestaurantOpenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private List<MenuItem> menu;

    private Time openingTime;

    private Time closingTime;

    private RestaurantOpenStatus openStatus;

    private RestaurantCategory category;

    private RestaurantCuisine cuisine;
}
