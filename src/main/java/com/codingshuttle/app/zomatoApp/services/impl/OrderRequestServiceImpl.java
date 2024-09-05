package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.OrderRequestDto;
import com.codingshuttle.app.zomatoApp.entities.*;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.exceptions.RuntimeConflictException;
import com.codingshuttle.app.zomatoApp.repositories.OrderRequestRepository;
import com.codingshuttle.app.zomatoApp.services.MenuItemService;
import com.codingshuttle.app.zomatoApp.services.OrderItemService;
import com.codingshuttle.app.zomatoApp.services.OrderRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
    @Transactional
    public OrderRequestDto addMenuItemToOrderRequest(Customer customer, Long menuItemId, int quantity) {
        log.info("Inside addMenuItemToOrderRequest of OrderRequestServiceImpl");
        try {
            MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
            Restaurant menuRestaurant = menuItem.getRestaurant();
            OrderRequest orderRequest = orderRequestRepository.findByCustomerAndOrderRequestStatus(customer, OrderRequestStatus.PENDING)
                    .orElseGet(() -> createNewOrderRequest(menuRestaurant, customer));

            if (!orderRequest.getRestaurant().equals(menuRestaurant)) {
                throw new RuntimeConflictException("Menu item doesn't belong to the restaurant with id:" + menuRestaurant.getId());
            }
            OrderItem orderItem = orderItemService.findByOrderRequestAndMenuItem(orderRequest, menuItem)
                    .orElseGet(() -> createNewOrderItem(orderRequest, menuItem));
            log.info("Order item: {}", orderItem);
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            OrderItem savedOrderItem = orderItemService.save(orderItem);
            orderRequest.getOrderItems().add(savedOrderItem);
            // Recalculate order totals, etc.
            orderRequest.updateOrderSummary();
            OrderRequest savedOrderRequest = orderRequestRepository.save(orderRequest);  // Save the OrderRequest to persist the updated order


            return modelMapper.map(savedOrderRequest, OrderRequestDto.class);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OrderItem createNewOrderItem(OrderRequest orderRequest, MenuItem menuItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderRequest(orderRequest);
        orderItem.setOrder(null);
        orderItem.setQuantity(0);
        orderItem.setMenuItem(menuItem);
        return orderItem;
    }

    private OrderRequest createNewOrderRequest(Restaurant restaurant, Customer customer) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderRequestStatus(OrderRequestStatus.PENDING);
        orderRequest.setCustomer(customer);
        orderRequest.setRestaurant(restaurant);
        orderRequest.setOrderItems(new ArrayList<>());
        orderRequest.setTotalItemCount(0);
        orderRequest.setTotalPrice(BigDecimal.valueOf(0.0));
        return orderRequestRepository.save(orderRequest);
    }

    @Override
    public OrderRequestDto deleteMenuItemFromOrderRequest(Customer customer, Long menuItemId) {
        OrderRequest orderRequest = orderRequestRepository.findByCustomerAndOrderRequestStatus(customer, OrderRequestStatus.PENDING)
                .orElseThrow(() -> new ResourceNotFoundException("OrderRequest not found for customer with id:"+customer.getId()));

        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        OrderItem orderItem = orderItemService.findByOrderRequestAndMenuItem(orderRequest, menuItem)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id:"+menuItemId));

        if(orderItem.getQuantity() > 1) {
            orderItem.setQuantity(orderItem.getQuantity() - 1);
            OrderItem savedOrderItem = orderItemService.save(orderItem);
        }else {
            orderItemService.delete(orderItem.getId());
            orderRequest.setOrderItems(orderRequest.getOrderItems().stream()
                    .filter(orderItem1 -> !orderItem1.getId().equals(orderItem.getId()))
                            .collect(Collectors.toList())

                    );
        }
        log.info("orderRequest is: {}", orderRequest);
        // Recalculate order totals, etc.
        orderRequest.updateOrderSummary();
        OrderRequest savedOrderRequest = orderRequestRepository.save(orderRequest);  // Save the OrderRequest to persist the updated order
        return modelMapper.map(savedOrderRequest, OrderRequestDto.class);
    }
}
