package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.entities.OrderItem;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.repositories.OrderItemRepository;
import com.codingshuttle.app.zomatoApp.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    @Override
    public Optional<OrderItem> findByOrderRequestAndMenuItem(OrderRequest orderRequest, MenuItem menuItem) {
        return orderItemRepository.findByOrderRequestAndMenuItem(orderRequest, menuItem);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void delete(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
