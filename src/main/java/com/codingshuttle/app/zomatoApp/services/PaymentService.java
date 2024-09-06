package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Payment;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentStatus;

import java.math.BigDecimal;

public interface PaymentService {
    void processPayment(Order order);

    void createNewPayment(Order order);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);

    void processTip(Customer customer, DeliveryExecutive deliveryExecutive, BigDecimal tipAmount);
}
