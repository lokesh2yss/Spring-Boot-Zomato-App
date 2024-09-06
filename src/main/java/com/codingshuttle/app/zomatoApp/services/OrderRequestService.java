package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.OrderRequestDto;
import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;

public interface OrderRequestService {
    OrderRequest findOrderRequestById(Long orderRequestId);

    OrderRequest findOrderRequestByCustomerAndRequestStatus(Customer customer, OrderRequestStatus orderRequestStatus);

    void update(OrderRequest orderRequest);

    OrderRequestDto addMenuItemToOrderRequest(Customer customer, Long menuItemId, int quantity);

    OrderRequestDto deleteMenuItemFromOrderRequest(Customer customer, Long menuItemId);
}
