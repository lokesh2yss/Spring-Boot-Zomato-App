package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Payment;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Order order);

    Payment createNewPayment(Order order);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
