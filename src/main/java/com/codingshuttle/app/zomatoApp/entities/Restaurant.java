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
@Table(name="restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuItem> menuItems;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Time openingTime;

    private Time closingTime;

    private RestaurantOpenStatus openStatus;

    private RestaurantCategory category;

    private RestaurantCuisine cuisine;
}
