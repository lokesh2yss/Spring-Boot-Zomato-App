package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.OrderRequest;

public interface OrderRequestService {
    OrderRequest findOrderRequestById(Long orderRequestId);

    void update(OrderRequest orderRequest);
}
