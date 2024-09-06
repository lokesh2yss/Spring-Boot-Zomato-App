package com.codingshuttle.app.zomatoApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private DeliveryExecutive deliveryExecutive;

    private Integer customerRating;

    private Integer restaurantRating;

    private Integer deliveryExecutiveRating;
}
