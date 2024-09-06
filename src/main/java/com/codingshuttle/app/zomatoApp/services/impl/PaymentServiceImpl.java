package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Payment;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionMethod;
import com.codingshuttle.app.zomatoApp.repositories.PaymentRepository;
import com.codingshuttle.app.zomatoApp.services.PaymentService;
import com.codingshuttle.app.zomatoApp.services.WalletService;
import com.codingshuttle.app.zomatoApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    private final WalletService walletService;
    @Override
    public void processPayment(Order order) {
        Payment payment = order.getPayment();
        paymentStrategyManager.paymentStrategy(order.getPaymentMethod()).processPayment(payment);

    }

    @Override
    public void createNewPayment(Order order) {
        Payment payment = Payment.builder()
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getTotalPrice())
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .build();
        paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);

    }

    @Override
    public void processTip(Customer customer, DeliveryExecutive deliveryExecutive, BigDecimal tipAmount) {
        walletService.deductMoneyFromWallet(customer.getUser(), tipAmount, null, null, TransactionMethod.TIP);
        walletService.addMoneyToWallet(deliveryExecutive.getUser(), tipAmount, null, null, TransactionMethod.TIP);
    }
}
