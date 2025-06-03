package com.example.jsonplaceholder.core.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {
    static {
        // H2 Database Configuration
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
        
        // JPA Configuration
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        System.setProperty("spring.jpa.show-sql", "true");
        System.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
        
        // Hikari Connection Pool Configuration
        System.setProperty("spring.datasource.hikari.connection-timeout", "20000");
        System.setProperty("spring.datasource.hikari.minimum-idle", "1");
        System.setProperty("spring.datasource.hikari.maximum-pool-size", "2");
        System.setProperty("spring.datasource.hikari.idle-timeout", "30000");
        System.setProperty("spring.datasource.hikari.max-lifetime", "60000");
        System.setProperty("spring.datasource.hikari.auto-commit", "true");
        System.setProperty("spring.datasource.hikari.connection-test-query", "SELECT 1");
    }
} 