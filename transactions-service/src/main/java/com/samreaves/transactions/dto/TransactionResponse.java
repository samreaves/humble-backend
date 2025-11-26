package com.samreaves.transactions.dto;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Category category;
    private TransactionType type;
    private LocalDateTime timestamp;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.category = transaction.getCategory();
        this.type = transaction.getType();
        this.timestamp = transaction.getTimestamp();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
