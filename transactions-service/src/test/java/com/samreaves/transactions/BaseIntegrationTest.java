package com.samreaves.transactions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    @ServiceConnection
    @SuppressWarnings("resource") // Container lifecycle managed by Testcontainers
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName(System.getenv().getOrDefault("POSTGRES_DB", "transactions"))
            .withUsername(System.getenv().getOrDefault("POSTGRES_USER", "myuser"))
            .withPassword(System.getenv().getOrDefault("POSTGRES_PASSWORD", "yoursupersecretpassword"));
}