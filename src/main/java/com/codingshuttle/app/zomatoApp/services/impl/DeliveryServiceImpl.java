package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.LiveLocationResponseDto;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.services.DeliveryExecutiveService;
import com.codingshuttle.app.zomatoApp.services.DeliveryService;
import com.codingshuttle.app.zomatoApp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderService orderService;
    private final DeliveryExecutiveService deliveryExecutiveService;
    @Override
    public LiveLocationResponseDto getDeliveryExecutiveLiveLocation(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        User user = getCurrentUser();
        if(!order.getRestaurant().getUser().equals(user) && !order.getCustomer().getUser().equals(user)) {
            throw new RuntimeException("User with id: "+user.getId()+" doesn't belong to this order, " +
                    "therefore cannot get delivery executive's live location");
        }
        return deliveryExecutiveService.getDeliveryExecutiveLiveLocation(order);
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
