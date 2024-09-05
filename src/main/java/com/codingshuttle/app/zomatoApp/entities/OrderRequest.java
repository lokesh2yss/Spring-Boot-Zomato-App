package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_requests")
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    private int totalItemCount;

    @OneToMany(mappedBy = "orderRequest", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private OrderRequestStatus orderRequestStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void updateOrderSummary() {
        BigDecimal newTotalPrice = BigDecimal.ZERO;
        int newTotalItemCount = 0;

        for (OrderItem item : orderItems) {
            newTotalPrice = newTotalPrice.add(item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            newTotalItemCount += item.getQuantity();
            System.out.println(newTotalItemCount+" and totalPrice="+newTotalPrice);
        }

        this.totalPrice = newTotalPrice;
        this.totalItemCount = newTotalItemCount;
    }

}
