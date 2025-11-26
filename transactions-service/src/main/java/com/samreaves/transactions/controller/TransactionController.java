package com.samreaves.transactions.controller;

import com.samreaves.transactions.service.TransactionService;
import com.samreaves.transactions.mapper.TransactionMapper;
import com.samreaves.transactions.entity.*;
import com.samreaves.transactions.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.lang.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(
        TransactionService transactionService,
        TransactionMapper transactionMapper
    ) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionResponse>> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        if (transactions != null) {
            return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(transactions.stream().map(transactionMapper::toResponse).collect(Collectors.toList()));
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<TransactionResponse> createTransaction(@NonNull @RequestBody TransactionCreateRequest transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transactionMapper.toEntity(transaction));
        if (createdTransaction != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(transactionMapper.toResponse(createdTransaction));
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
