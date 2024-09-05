package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.OrderRequestDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;

public interface OrderRequestService {
    OrderRequest findOrderRequestById(Long orderRequestId);

    void update(OrderRequest orderRequest);

    OrderRequestDto addMenuItemToOrderRequest(Customer customer, Long menuItemId, int quantity);

    OrderRequestDto deleteMenuItemFromOrderRequest(Customer customer, Long menuItemId);
}
