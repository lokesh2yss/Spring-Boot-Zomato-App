package com.codingshuttle.app.zomatoApp.strategies;

import com.codingshuttle.app.zomatoApp.entities.enums.PaymentMethod;
import com.codingshuttle.app.zomatoApp.strategies.impl.CashPaymentStrategy;
import com.codingshuttle.app.zomatoApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch(paymentMethod) {
            case PaymentMethod.COD -> cashPaymentStrategy;
            case PaymentMethod.WALLET -> walletPaymentStrategy;
        };
    }
}
