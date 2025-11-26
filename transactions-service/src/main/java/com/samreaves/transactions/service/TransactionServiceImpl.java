package com.samreaves.transactions.service;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(@NonNull Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByCategory(@NonNull Category category) {
        return transactionRepository.findByCategory(category);
    }

    public List<Transaction> getTransactionsByType(@NonNull TransactionType type) {
        return transactionRepository.findByType(type);
    }
}
