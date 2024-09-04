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
    public Wallet createNewWallet(User user) {
        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.valueOf(0.0))
                .build();
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wallet not found for user with id: "+user.getId()));
    }

    @Override
    public Wallet addMoneyToWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance().add(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .transactionRefId(null)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(TransactionMethod.ORDER)
                .order(order)
                .build();

        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, BigDecimal amount, Order order, String transactionId, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance().subtract(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .transactionRefId(null)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(TransactionMethod.ORDER)
                .order(order)
                .build();

        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }
}
