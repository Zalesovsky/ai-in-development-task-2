package com.example.jsonplaceholder.core.repository;

import com.example.jsonplaceholder.core.model.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyRepositoryTest extends AbstractPostgresContainerTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("Should save and retrieve Company")
    void saveAndFindById_whenCompany_shouldPersistAndRetrieve() {
        Company company = new Company();
        company.setName("Test Company");
        company.setCatchPhrase("Catch the best");
        company.setBs("bs-value");

        Company saved = companyRepository.save(company);
        Optional<Company> found = companyRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Company");
        assertThat(found.get().getCatchPhrase()).isEqualTo("Catch the best");
    }
} 