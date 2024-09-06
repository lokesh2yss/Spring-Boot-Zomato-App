package com.codingshuttle.app.zomatoApp.strategies.impl;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.Payment;
import com.codingshuttle.app.zomatoApp.entities.enums.PaymentStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionMethod;
import com.codingshuttle.app.zomatoApp.repositories.PaymentRepository;
import com.codingshuttle.app.zomatoApp.services.PaymentService;
import com.codingshuttle.app.zomatoApp.services.WalletService;
import com.codingshuttle.app.zomatoApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//Cash paid to deliveryExecutive = 500
//
@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Order order = payment.getOrder();
        BigDecimal platformCommission = payment.getAmount().subtract(DELIVERY_EXECUTIVE_FEE).multiply(BigDecimal.valueOf(PLATFORM_COMMISSION));
        BigDecimal amountPayableToRestaurant = payment.getAmount().subtract(DELIVERY_EXECUTIVE_FEE).subtract(platformCommission);

        walletService.deductMoneyFromWallet(order.getDeliveryExecutive().getUser(), platformCommission.add(amountPayableToRestaurant), order, null, TransactionMethod.ORDER);
        walletService.addMoneyToWallet(order.getRestaurant().getUser(), amountPayableToRestaurant, order, null, TransactionMethod.ORDER);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
