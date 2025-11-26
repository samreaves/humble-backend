package com.samreaves.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByType(TransactionType type);
}
