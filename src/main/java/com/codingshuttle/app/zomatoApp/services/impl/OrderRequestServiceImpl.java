package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.MenuItemDto;
import com.codingshuttle.app.zomatoApp.dto.OrderRequestDto;
import com.codingshuttle.app.zomatoApp.entities.*;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.OrderRequestRepository;
import com.codingshuttle.app.zomatoApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {
    private final OrderRequestRepository orderRequestRepository;
    private final MenuItemService menuItemService;
    private final OrderItemService orderItemService;
    private final ModelMapper modelMapper;

    @Override
    public OrderRequest findOrderRequestById(Long orderRequestId) {
        return orderRequestRepository.findById(orderRequestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("OrderRequest not found with id:"+orderRequestId));
    }

    @Override
    public void update(OrderRequest orderRequest) {
        orderRequestRepository.findById(orderRequest.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("OrderRequest not found with id:"+orderRequest.getId()));
        orderRequestRepository.save(orderRequest);
    }

    @Override
    public OrderRequestDto addMenuItemToOrderRequest(Restaurant restaurant, Customer customer, Long menuItemId, int quantity) {
        OrderRequest orderRequest = orderRequestRepository.findByCustomerAndOrderRequestStatus(customer, OrderRequestStatus.PENDING)
                .orElseGet(() -> createNewOrderRequest(restaurant, customer));

        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        OrderItem orderItem = orderItemService.findByOrderRequestAndMenuItem(orderRequest, menuItem)
                .orElseGet(() -> createNewOrderItem(orderRequest, menuItem));
        orderItem.setQuantity(orderItem.getQuantity() + quantity);
        OrderItem savedOrderItem = orderItemService.save(orderItem);

        // Recalculate order totals, etc.
        orderRequest.updateOrderSummary();
        OrderRequest savedOrderRequest = orderRequestRepository.save(orderRequest);  // Save the OrderRequest to persist the updated order
        return modelMapper.map(savedOrderRequest, OrderRequestDto.class);
    }

    private OrderItem createNewOrderItem(OrderRequest orderRequest, MenuItem menuItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderRequest(orderRequest);
        orderItem.setOrder(null);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(0);
        return orderItem;
    }

    private OrderRequest createNewOrderRequest(Restaurant restaurant, Customer customer) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderRequestStatus(OrderRequestStatus.PENDING);
        orderRequest.setCustomer(customer);
        orderRequest.setRestaurant(restaurant);
        orderRequest.setCreatedAt(LocalDateTime.now());
        return orderRequest;
    }

    @Override
    public OrderRequestDto deleteMenuItemFromOrderRequest(Restaurant restaurant, Customer customer, Long menuItemId) {
        return null;
    }
}
