package com.samreaves.transactions.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

enum Category {
    GROCERIES,
    RESTAURANTS,
    HOUSE,
    CAR,
    VACATION,
    GIVING,
    BACKPACKING,
    THREE_D_PRINTING,
    CLOTHING,
    TAXES,
    MUSIC,
    PHONE,
    HAIRCUT,
    TV,
    FUN,
    COFFEE,
    NIGHTSOUT,
    EDUCATION
}

enum TransactionType {
    DEBIT,
    CREDIT
}

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}