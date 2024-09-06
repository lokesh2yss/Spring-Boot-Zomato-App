package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderDeliveryStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByDeliveryExecutive(DeliveryExecutive deliveryExecutive, Pageable pageRequest);

    Page<Order> findByRestaurant(Restaurant restaurant, Pageable pageRequest);

    Page<Order> findByCustomer(Customer customer, Pageable pageRequest);

    Page<Order> findByCustomerAndOrderStatusNotInAndOrderDeliveryStatusNotIn(Customer customer, List<OrderStatus> cancelled, List<OrderDeliveryStatus> delivered,
                                                                             Pageable pageable);
}
