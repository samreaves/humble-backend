package com.samreaves.transactions.controller;

import com.samreaves.transactions.service.TransactionService;
import com.samreaves.transactions.mapper.TransactionMapper;
import com.samreaves.transactions.entity.*;
import com.samreaves.transactions.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;


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
    public ResponseEntity<List<TransactionResponse>> getTransactions(
        @RequestParam(required = false) Category category,
        @RequestParam(required = false) TransactionType type,
        @RequestParam(required = false) BigDecimal amount,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) LocalDateTime timestamp
    ) {
        // Param null check exists in TransactionService via TransactionSpecifications' use of JPA CriteriaBuilder conjunction
        List<Transaction> transactions = transactionService.getTransactions(category, type, amount, description, timestamp);
        return ResponseEntity.status(HttpStatus.OK)
            .body(transactions.stream().map(transactionMapper::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("")
    public ResponseEntity<TransactionResponse> createTransaction(@NonNull @RequestBody TransactionCreateRequest transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transactionMapper.toEntity(transaction));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(transactionMapper.toResponse(createdTransaction));
    }
}
