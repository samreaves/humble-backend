package com.samreaves.transactions.service;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.repository.TransactionRepository;
import com.samreaves.transactions.specification.TransactionSpecifications;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(@NonNull Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(
        Category category,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDateTime timestamp
    ) {
        return transactionRepository.findAll(
            TransactionSpecifications.hasCategory(category)
            .and(TransactionSpecifications.hasType(type))
            .and(TransactionSpecifications.hasAmount(amount))
            .and(TransactionSpecifications.hasDescription(description))
            .and(TransactionSpecifications.hasTimestamp(timestamp))
        );
    }
}
