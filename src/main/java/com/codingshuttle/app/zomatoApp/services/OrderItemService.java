package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.entities.OrderItem;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;

import java.util.Optional;

public interface OrderItemService {
    Optional<OrderItem> findByOrderRequestAndMenuItem(OrderRequest orderRequest, MenuItem menuItem);

    OrderItem save(OrderItem orderItem);
}
