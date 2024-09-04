package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.*;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    Order getOrderById(Long orderId);

    Order createNewOrder(OrderRequest orderRequest, PaymentMethod paymentMethod, Long addressId);

    Order updateOrderStatus(Order order, OrderStatus orderStatus);

    Page<Order> getAllOrdersOfCustomer(Customer customer, PageRequest pageRequest);

    Page<Order> getAllOrdersOfRestaurant(Restaurant restaurant, PageRequest pageRequest);

    Page<Order> getAllOrdersOfDeliveryExecutive(DeliveryExecutive deliveryExecutive, PageRequest pageRequest);

    Order updateOrder(Order order);
}
