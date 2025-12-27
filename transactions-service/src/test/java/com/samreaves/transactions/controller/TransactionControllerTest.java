package com.samreaves.transactions.controller;

import com.samreaves.transactions.dto.TransactionResponse;
import com.samreaves.transactions.dto.TransactionCreateRequest;
import com.samreaves.transactions.service.TransactionService;
import com.samreaves.transactions.mapper.TransactionMapper;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.testutil.TransactionTestBuilder;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuppressWarnings("null")
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

    /* Happy Path Test for creating a transaction */
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

    /* Error Path Test for creating a transaction with invalid JSON */
    @Test
    void shouldReturnBadRequestForInvalidJson() throws Exception {
    mockMvc.perform(post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{invalid json"))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for creating a transaction with invalid category */
    @Test
    void shouldReturnBadRequestForInvalidCategory() throws Exception {
    mockMvc.perform(post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "amount": 2000.00,
                    "description": "Mortgage",
                    "category": "INVALID",
                    "type": "DEBIT",
                    "timestamp": "2024-01-01T12:00:00"
                }
                """
            ))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for creating a transaction with invalid transaction type */
    @Test
    void shouldReturnBadRequestForInvalidTransactionType() throws Exception {
    mockMvc.perform(post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "amount": 2000.00,
                    "description": "Mortgage",
                    "category": "HOUSE",
                    "type": "INVALID",
                    "timestamp": "2024-01-01T12:00:00"
                }
                """
            ))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for creating a transaction with missing required fields */
    @Test
    void shouldReturnBadRequestForMissingRequiredFields() throws Exception {
    mockMvc.perform(post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "description": "Mortgage",
                    "category": "HOUSE",
                    "type": "INVALID",
                    "timestamp": "2024-01-01T12:00:00"
                }
                """
            ))
        .andExpect(status().isBadRequest());
    }

    /* Happy Path Test for getting a list of transactions */
    @Test
    void shouldReturnListOfTransactions() throws Exception {
        Transaction transaction = transactionBuilder().build();
        when(transactionService.getTransactions(any(Category.class), any(TransactionType.class), any(BigDecimal.class), any(String.class), any(LocalDateTime.class)))
            .thenReturn(List.of(transaction));
        when(transactionMapper.toResponse(any(Transaction.class)))
            .thenReturn(new TransactionResponse(transaction));

        mockMvc.perform(
            get("/transactions")
                .param("category", "HOUSE")
                .param("type", "DEBIT")
                .param("amount", "2000.00")
                .param("description", "Mortgage")
                .param("timestamp", "2024-01-01T12:00:00")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].description").value("Mortgage"))
            .andExpect(jsonPath("$[0].amount").value(2000.00))
            .andExpect(jsonPath("$[0].category").value("HOUSE"))
            .andExpect(jsonPath("$[0].type").value("DEBIT"))
            .andExpect(jsonPath("$[0].timestamp").value("2024-01-01T12:00:00")
        );
    }

    /* Happy Path Test for getting a list of transactions with no transactions */
    @Test
    void shouldReturnEmptyListForNoTransactions() throws Exception {
    mockMvc.perform(get("/transactions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0));
    }

    /* Error Path Test for getting a list of transactions with invalid category */
    @Test
    void shouldReturnBadRequestForGetTransactionsWithInvalidCategory() throws Exception {
    mockMvc.perform(get("/transactions")
            .param("category", "INVALID"))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for getting a list of transactions with invalid transaction type */
    @Test
    void shouldReturnBadRequestForGetTransactionsWithInvalidTransactionType() throws Exception {
    mockMvc.perform(get("/transactions")
            .param("type", "INVALID"))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for getting a list of transactions with invalid amount */
    @Test
    void shouldReturnBadRequestForGetTransactionsWithInvalidAmount() throws Exception {
    mockMvc.perform(get("/transactions")
            .param("amount", "INVALID"))
        .andExpect(status().isBadRequest());
    }

    /* Error Path Test for getting a list of transactions with invalid timestamp */
    @Test
    void shouldReturnBadRequestForGetTransactionsWithInvalidTimestamp() throws Exception {
    mockMvc.perform(get("/transactions")
            .param("timestamp", "INVALID"))
        .andExpect(status().isBadRequest());
    }

    /* Non-Error Path Test for getting a list of transactions with crazy description */
    @Test
    void shouldReturnBadRequestForGetTransactionsWithInvalidDescription() throws Exception {
    mockMvc.perform(get("/transactions")
            .param("description", "245sgal1403sgsags"))
        .andExpect(status().isOk());
    }
}
