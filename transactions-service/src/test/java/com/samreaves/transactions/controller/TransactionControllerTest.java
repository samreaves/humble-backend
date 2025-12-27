package com.samreaves.transactions.controller;

import com.samreaves.transactions.dto.TransactionResponse;
import com.samreaves.transactions.dto.TransactionCreateRequest;
import com.samreaves.transactions.service.TransactionService;
import com.samreaves.transactions.mapper.TransactionMapper;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.testutil.TransactionTestBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Objects;


@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @MockitoBean
    private TransactionMapper transactionMapper;

    private TransactionTestBuilder transactionBuilder() {
        return new TransactionTestBuilder();
    }

    @SuppressWarnings("null")
    @Test
    void shouldCreateTransactionSuccessfully() throws Exception {
        Transaction mockTransaction = transactionBuilder().build();
        TransactionCreateRequest request = transactionBuilder().buildCreateRequest(mockTransaction);
        TransactionResponse expectedResponse = new TransactionResponse(mockTransaction);

        when(transactionMapper.toEntity(any(TransactionCreateRequest.class)))
            .thenReturn(mockTransaction);
        when(transactionService.createTransaction(notNull(Transaction.class)))
            .thenReturn(mockTransaction);
        when(transactionMapper.toResponse(any(Transaction.class)))
            .thenReturn(expectedResponse);
        
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value("Mortgage"))
            .andExpect(jsonPath("$.amount").value(2000.00))
            .andExpect(jsonPath("$.category").value("HOUSE"))
            .andExpect(jsonPath("$.type").value("DEBIT"))
            .andExpect(jsonPath("$.timestamp").value("2024-01-01T12:00:00"));
    }
}
