package com.samreaves.transactions.service;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import org.springframework.lang.NonNull;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface TransactionService {

    public Transaction createTransaction(@NonNull Transaction transaction);

    public List<Transaction> getTransactions(
        Category category,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDateTime timestamp
    );
}
