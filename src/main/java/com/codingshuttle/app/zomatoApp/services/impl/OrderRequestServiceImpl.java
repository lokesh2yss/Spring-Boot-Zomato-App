package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.OrderRequestRepository;
import com.codingshuttle.app.zomatoApp.services.OrderRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {
    private final OrderRequestRepository orderRequestRepository;
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
}
