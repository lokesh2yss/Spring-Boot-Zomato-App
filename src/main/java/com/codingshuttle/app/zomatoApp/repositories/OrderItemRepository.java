package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.entities.OrderItem;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderRequestAndMenuItem(OrderRequest orderRequest, MenuItem menuItem);
}
