package com.samreaves.transactions.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.*;
import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import com.samreaves.transactions.BaseIntegrationTest;
import com.samreaves.transactions.testutil.TransactionTestBuilder;
import com.samreaves.transactions.specification.TransactionSpecifications;

import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


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
    }

    @Test
    public void shouldSaveAndRetrieveTransaction() {
        transactionBuilder().buildAndSave(transactionsRepository);
        assertThat(transactionsRepository.findAll()).hasSize(1);

        Transaction savedTransaction = transactionsRepository.findAll().getFirst();

        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(2000.00));
        assertThat(savedTransaction.getDescription()).isEqualTo("Mortgage");
        assertThat(savedTransaction.getCategory()).isEqualTo(Category.HOUSE);
        assertThat(savedTransaction.getType()).isEqualTo(TransactionType.DEBIT);
        assertThat(savedTransaction.getTimestamp()).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0));
    }

    @Test
    public void shouldSaveAndRetrieveTransactionsByCategory() {
        transactionBuilder().withCategory(Category.HOUSE).buildAndSave(transactionsRepository);
        transactionBuilder().withCategory(Category.HOUSE).buildAndSave(transactionsRepository);
        transactionBuilder().withCategory(Category.EDUCATION).buildAndSave(transactionsRepository);

        List<Transaction> transactions = transactionsRepository.findAll(TransactionSpecifications.hasCategory(Category.HOUSE));
        List<Transaction> allTransactions = transactionsRepository.findAll();
        assertThat(transactions).hasSize(2);
        assertThat(allTransactions).hasSize(3);
        assertThat(transactions.get(0).getCategory()).isEqualTo(Category.HOUSE);
        assertThat(transactions.get(1).getCategory()).isEqualTo(Category.HOUSE);
        assertThat(allTransactions).filteredOn(transaction -> transaction.getCategory() == Category.EDUCATION).hasSize(1);
    }

    @Test
    public void shouldSaveAndRetrieveTransactionsByType() {
        transactionBuilder().asCredit().buildAndSave(transactionsRepository);
        transactionBuilder().asCredit().buildAndSave(transactionsRepository);
        transactionBuilder().asDebit().buildAndSave(transactionsRepository);

        List<Transaction> transactions = transactionsRepository.findAll(TransactionSpecifications.hasType(TransactionType.CREDIT));
        List<Transaction> allTransactions = transactionsRepository.findAll();
        assertThat(transactions).hasSize(2);
        assertThat(allTransactions).hasSize(3);
        assertThat(allTransactions).extracting(Transaction::getType).containsExactlyInAnyOrder(TransactionType.CREDIT, TransactionType.CREDIT, TransactionType.DEBIT);
        assertThat(transactions.get(0).getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transactions.get(1).getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(allTransactions).filteredOn(transaction -> transaction.getType() == TransactionType.DEBIT).hasSize(1);
    }

    @Test
    public void shouldSaveAndRetrieveTransactionsByCategoryAndType() {
        transactionBuilder().asDebit().withCategory(Category.HOUSE).buildAndSave(transactionsRepository);
        transactionBuilder().asDebit().withCategory(Category.EDUCATION).buildAndSave(transactionsRepository);
        transactionBuilder().asDebit().withCategory(Category.EDUCATION).buildAndSave(transactionsRepository);
        transactionBuilder().asCredit().buildAndSave(transactionsRepository);

        List<Transaction> transactions = transactionsRepository.findAll(TransactionSpecifications
            .hasType(TransactionType.DEBIT)
            .and(TransactionSpecifications.hasCategory(Category.EDUCATION)));

        List<Transaction> allTransactions = transactionsRepository.findAll();
        assertThat(transactions).hasSize(2);
        assertThat(allTransactions).hasSize(4);
        assertThat(transactions.get(0).getType()).isEqualTo(TransactionType.DEBIT);
        assertThat(transactions.get(0).getCategory()).isEqualTo(Category.EDUCATION);
        assertThat(transactions.get(1).getType()).isEqualTo(TransactionType.DEBIT);
        assertThat(transactions.get(1).getCategory()).isEqualTo(Category.EDUCATION);
        assertThat(allTransactions).filteredOn(transaction -> transaction.getType() == TransactionType.CREDIT).hasSize(1);
        assertThat(allTransactions).filteredOn(transaction -> transaction.getType() == TransactionType.DEBIT && transaction.getCategory() == Category.HOUSE).hasSize(1);
    }
}