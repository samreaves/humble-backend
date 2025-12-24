package com.samreaves.transactions.testutil;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.repository.TransactionRepository;
import com.samreaves.transactions.dto.TransactionCreateRequest;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionTestBuilder {

    // Default values for a basic mortgage transaction
    private String description = "Mortgage";
    private BigDecimal amount = new BigDecimal("2000.00");
    private Category category = Category.HOUSE;
    private TransactionType type = TransactionType.DEBIT;
    private LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0);

    /**
     * Helper methods that allow fluid chaining of builder methods.
     * Example:
     * <code>
     * transactionTestBuilder().asCredit().withAmount(new BigDecimal("1000")).buildAndSave(repository);
     * </code>
     */
    public TransactionTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TransactionTestBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionTestBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public TransactionTestBuilder withType(TransactionType type) {
        this.type = type;
        return this;
    }

    public TransactionTestBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TransactionTestBuilder asCredit() {
        this.type = TransactionType.CREDIT;
        return this;
    }

    public TransactionTestBuilder asDebit() {
        this.type = TransactionType.DEBIT;
        return this;
    }


    // Convenience methods to build and save a transaction
    public @NonNull Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setTimestamp(timestamp);
        return transaction;
    }

    public @NonNull TransactionCreateRequest buildCreateRequest(@NonNull Transaction transaction) {
        TransactionCreateRequest request = new TransactionCreateRequest();
        request.setDescription(transaction.getDescription());
        request.setAmount(transaction.getAmount());
        request.setCategory(transaction.getCategory());
        request.setType(transaction.getType());
        request.setTimestamp(transaction.getTimestamp());
        return request;
    }

    public @NonNull Transaction buildAndSave(TransactionRepository repository) {
        return repository.save(build());
    }
}
