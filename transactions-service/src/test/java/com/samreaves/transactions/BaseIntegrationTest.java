package com.samreaves.transactions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
public abstract class BaseIntegrationTest {

    @SuppressWarnings("resource") // Container is managed by Testcontainers and cleaned up via Ryuk
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName(System.getenv().getOrDefault("POSTGRES_DB", "transactions"))
            .withUsername(System.getenv().getOrDefault("POSTGRES_USER", "myuser"))
            .withPassword(System.getenv().getOrDefault("POSTGRES_PASSWORD", "yoursupersecretpassword"));

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}