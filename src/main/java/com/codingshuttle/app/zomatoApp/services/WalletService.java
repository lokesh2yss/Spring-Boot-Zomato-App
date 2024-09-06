package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.Wallet;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionMethod;

import java.math.BigDecimal;

public interface WalletService {
    void createNewWallet(User user);
    Wallet findWalletByUser(User user);

    void addMoneyToWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod);

    void deductMoneyFromWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod);


}
