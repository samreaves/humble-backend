package com.samreaves.transactions.specification;

import org.springframework.data.jpa.domain.Specification;

import com.samreaves.transactions.entity.Transaction;
import com.samreaves.transactions.entity.Category;
import com.samreaves.transactions.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionSpecifications {
    public static Specification<Transaction> hasCategory(Category category) {
        return (root, query, criteriaBuilder) ->
            category == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Transaction> hasType(TransactionType type) {
        return (root, query, criteriaBuilder) ->
            type == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Transaction> hasAmount(BigDecimal amount) {
        return (root, query, criteriaBuilder) ->
            amount == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("amount"), amount);
    }

    public static Specification<Transaction> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
            description == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThan(
                    criteriaBuilder.function("word_similarity", Double.class, 
                        criteriaBuilder.literal(description.toLowerCase()),
                        criteriaBuilder.lower(
                            root.get("description")
                        )
                    ),
                    0.5
                );
    }

    public static Specification<Transaction> hasTimestamp(LocalDateTime timestamp) {
        return (root, query, criteriaBuilder) ->
            timestamp == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("timestamp"), timestamp);
    }
}
