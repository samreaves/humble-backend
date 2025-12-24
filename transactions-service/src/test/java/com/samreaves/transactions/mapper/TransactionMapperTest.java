package com.samreaves.transactions.mapper;

import com.samreaves.transactions.mapper.TransactionMapper;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.dto.TransactionResponse;
import com.samreaves.transactions.dto.TransactionCreateRequest;
import com.samreaves.transactions.testutil.TransactionTestBuilder;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class TransactionMapperTest {

    private TransactionMapper transactionMapper;
    private TransactionTestBuilder transactionBuilder() {
        return new TransactionTestBuilder();
    }

    @BeforeEach
    public void setUp() {
        transactionMapper = new TransactionMapper();
    }

    @Test
    public void shouldMapTransactionToResponse() {
        Transaction transaction = transactionBuilder().build();

        TransactionResponse response = transactionMapper.toResponse(transaction);

        assertThat(response.getId()).isEqualTo(transaction.getId());
        assertThat(response.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(response.getAmount()).isEqualTo(transaction.getAmount());
        assertThat(response.getCategory()).isEqualTo(transaction.getCategory());
        assertThat(response.getType()).isEqualTo(transaction.getType());
        assertThat(response.getTimestamp()).isEqualTo(transaction.getTimestamp());
    }

    @Test
    public void shouldMapTransactionCreateRequestToEntity() {
        Transaction transaction = transactionBuilder().build();
        TransactionCreateRequest request = transactionBuilder().buildCreateRequest(transaction);

        Transaction entity = transactionMapper.toEntity(request);

        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getAmount()).isEqualTo(request.getAmount());
        assertThat(entity.getCategory()).isEqualTo(request.getCategory());
        assertThat(entity.getType()).isEqualTo(request.getType());
        assertThat(entity.getTimestamp()).isEqualTo(request.getTimestamp());
    }
}