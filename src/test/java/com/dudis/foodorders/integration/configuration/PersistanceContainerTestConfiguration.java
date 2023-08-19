package com.dudis.foodorders.integration.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class PersistanceContainerTestConfiguration {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String POSTGRESQL = "postgresql";
    public static final String POSTGRESQL_CONTAINER = "postgres:15.4";


    @SuppressWarnings("resource")
    @Bean
    @Qualifier(POSTGRESQL)
    PostgreSQLContainer<?> postgreSQLContainer() {
        try (PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(POSTGRESQL_CONTAINER)
            .withUsername(USERNAME)
            .withPassword(PASSWORD)) {
            postgresqlContainer.start();
            System.setProperty("spring.datasource.username", postgresqlContainer.getUsername());
            System.setProperty("spring.datasource.password", postgresqlContainer.getPassword());
            return postgresqlContainer;
        }
    }

    @Bean
    DataSource dataSource(final PostgreSQLContainer<?> postgreSQLContainer) {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .driverClassName(postgreSQLContainer.getDriverClassName())
            .url(postgreSQLContainer.getJdbcUrl())
            .username(postgreSQLContainer.getUsername())
            .password(postgreSQLContainer.getPassword())
            .build();
    }
}
