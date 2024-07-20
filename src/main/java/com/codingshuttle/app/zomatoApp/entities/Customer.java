package com.codingshuttle.app.zomatoApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Entity
    @Getter
    @Setter
    public static class DeliveryExecutive {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Double rating;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;
    }
}
