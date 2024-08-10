package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "delivery_executive_id")
    private DeliveryExecutive deliveryExecutive;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private Double totalAmount;

    @OneToOne(mappedBy = "order", optional = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String pickupOtp;

    private String deliveryOtp;

    @CreationTimestamp
    private LocalDateTime placedAt;

    private LocalDateTime pickedAt;

    private LocalDateTime deliveredAt;
}

