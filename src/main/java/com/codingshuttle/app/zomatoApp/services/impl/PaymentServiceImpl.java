package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Payment;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentStatus;
import com.codingshuttle.app.zomatoApp.repositories.PaymentRepository;
import com.codingshuttle.app.zomatoApp.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Order order) {

    }

    @Override
    public Payment createNewPayment(Order order) {
        return null;
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {

    }
}
