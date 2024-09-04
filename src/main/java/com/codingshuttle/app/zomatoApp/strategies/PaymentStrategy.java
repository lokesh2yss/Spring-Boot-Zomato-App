package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.entities.Payment;

import java.math.BigDecimal;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    BigDecimal DELIVERY_EXECUTIVE_FEE = BigDecimal.valueOf(30.0);
    void processPayment(Payment payment);
}
