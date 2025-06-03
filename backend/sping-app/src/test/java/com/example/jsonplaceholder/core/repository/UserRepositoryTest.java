package com.example.jsonplaceholder.core.repository;

import com.example.jsonplaceholder.core.model.Address;
import com.example.jsonplaceholder.core.model.Company;
import com.example.jsonplaceholder.core.model.Geo;
import com.example.jsonplaceholder.core.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractPostgresContainerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve User with Address and Company")
    void saveAndFindById_whenUserWithAddressAndCompany_shouldPersistAndRetrieve() {
        // Create Geo
        Geo geo = new Geo();
        geo.setLat("10.123");
        geo.setLng("20.456");

        // Create Address
        Address address = new Address();
        address.setStreet("Main St");
        address.setSuite("Apt 1");
        address.setCity("Testville");
        address.setZipcode("12345");
        address.setGeo(geo);

        // Create Company
        Company company = new Company();
        company.setName("Test Company");
        company.setCatchPhrase("Catch the best");
        company.setBs("bs-value");

        // Create User
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPhone("123-456-7890");
        user.setWebsite("example.com");
        user.setAddress(address);
        user.setCompany(company);

        User saved = userRepository.save(user);
        Optional<User> found = userRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
        assertThat(found.get().getLastName()).isEqualTo("Doe");
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
        assertThat(found.get().getPhone()).isEqualTo("123-456-7890");
        assertThat(found.get().getWebsite()).isEqualTo("example.com");
        
        // Check Address
        assertThat(found.get().getAddress()).isNotNull();
        assertThat(found.get().getAddress().getStreet()).isEqualTo("Main St");
        assertThat(found.get().getAddress().getGeo()).isNotNull();
        assertThat(found.get().getAddress().getGeo().getLat()).isEqualTo("10.123");
        
        // Check Company
        assertThat(found.get().getCompany()).isNotNull();
        assertThat(found.get().getCompany().getName()).isEqualTo("Test Company");
        assertThat(found.get().getCompany().getCatchPhrase()).isEqualTo("Catch the best");
    }
} 