package com.samreaves.transactions.service;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.util.List;

@Service
public interface TransactionService {

    public Transaction createTransaction(@NonNull Transaction transaction);

    public List<Transaction> getTransactions();

    public List<Transaction> getTransactionsByCategory(@NonNull Category category);

    public List<Transaction> getTransactionsByType(@NonNull TransactionType type);
}
