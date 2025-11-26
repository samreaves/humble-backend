package com.samreaves.transactions.dto;

import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionCreateRequest {
    private String description;
    private BigDecimal amount;
    private Category category;
    private TransactionType type;
    private LocalDateTime timestamp;

    public TransactionCreateRequest(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}