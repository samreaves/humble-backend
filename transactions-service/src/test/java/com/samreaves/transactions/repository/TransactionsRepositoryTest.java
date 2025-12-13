package com.samreaves.transactions.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import com.samreaves.transactions.repository.TransactionRepository;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.BaseIntegrationTest;
import com.samreaves.transactions.testutil.TransactionTestBuilder;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class TransactionsRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private TransactionRepository transactionsRepository;

    private TransactionTestBuilder transactionBuilder() {
        return new TransactionTestBuilder();
    }

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        transactionsRepository.deleteAll();

        // Or seed test data if you want
        transactionBuilder().buildAndSave(transactionsRepository);
    }

    @Test
    public void shouldSaveAndRetrieveTransaction() {
        assertThat(transactionsRepository.findAll()).hasSize(1);

        Transaction savedTransaction = transactionsRepository.findAll().getFirst();

        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(2000.00));
        assertThat(savedTransaction.getDescription()).isEqualTo("Mortgage");
        assertThat(savedTransaction.getCategory()).isEqualTo(Category.HOUSE);
        assertThat(savedTransaction.getType()).isEqualTo(TransactionType.DEBIT);
        assertThat(savedTransaction.getTimestamp()).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0));
    }
}