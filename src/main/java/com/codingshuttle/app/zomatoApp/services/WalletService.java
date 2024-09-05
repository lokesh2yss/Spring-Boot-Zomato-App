package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.Wallet;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionMethod;

import java.math.BigDecimal;

public interface WalletService {
    Wallet createNewWallet(User user);
    Wallet findWalletByUser(User user);

    Wallet addMoneyToWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod);


}
