package com.samreaves.transactions.dto;

import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.PastOrPresent;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TransactionCreateRequest {
    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "9999999999.99")
    private BigDecimal amount;

    @NotNull
    private Category category;

    @NotNull
    private TransactionType type;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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