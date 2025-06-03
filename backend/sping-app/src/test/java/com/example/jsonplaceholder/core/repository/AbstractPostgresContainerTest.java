package com.example.jsonplaceholder.core.repository;

import com.example.jsonplaceholder.core.integration.AbstractIntegrationTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractPostgresContainerTest extends AbstractIntegrationTest {
} 