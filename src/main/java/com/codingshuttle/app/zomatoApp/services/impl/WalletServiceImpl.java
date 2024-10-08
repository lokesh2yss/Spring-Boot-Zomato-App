package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.Order;
import com.codingshuttle.app.zomatoApp.entities.User;
import com.codingshuttle.app.zomatoApp.entities.Wallet;
import com.codingshuttle.app.zomatoApp.entities.WalletTransaction;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionMethod;
import com.codingshuttle.app.zomatoApp.entities.enums.TransactionType;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.WalletRepository;
import com.codingshuttle.app.zomatoApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    @Override
    public void createNewWallet(User user) {
        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.valueOf(0.0))
                .build();
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wallet not found for user with id: "+user.getId()));
    }

    @Override
    public void addMoneyToWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance().add(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .transactionRefId(transactionId)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .order(order)
                .build();

        wallet.getTransactions().add(walletTransaction);

        walletRepository.save(wallet);
    }

    @Override
    public void deductMoneyFromWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance().subtract(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .transactionRefId(transactionId)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .order(order)
                .build();

        wallet.getTransactions().add(walletTransaction);

        walletRepository.save(wallet);
    }
}
