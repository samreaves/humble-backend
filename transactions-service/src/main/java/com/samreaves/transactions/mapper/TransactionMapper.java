package com.samreaves.transactions.mapper;

import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.dto.TransactionResponse;
import com.samreaves.transactions.dto.TransactionCreateRequest;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(transaction);
    }
    
    @NonNull
    public Transaction toEntity(TransactionCreateRequest request) {
        Transaction transaction = new Transaction();
        transaction.setDescription(request.getDescription());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setType(request.getType());
        transaction.setTimestamp(request.getTimestamp());
        return transaction;
    }

}
