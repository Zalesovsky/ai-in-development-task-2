package com.example.jsonplaceholder.core.repository;

import com.example.jsonplaceholder.core.model.Address;
import com.example.jsonplaceholder.core.model.Geo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressRepositoryTest extends AbstractPostgresContainerTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should save and retrieve Address with Geo")
    void saveAndFindById_whenAddressWithGeo_shouldPersistAndRetrieve() {
        Geo geo = new Geo();
        geo.setLat("10.123");
        geo.setLng("20.456");

        Address address = new Address();
        address.setStreet("Main St");
        address.setSuite("Apt 1");
        address.setCity("Testville");
        address.setZipcode("12345");
        address.setGeo(geo);

        Address saved = addressRepository.save(address);
        Optional<Address> found = addressRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getStreet()).isEqualTo("Main St");
        assertThat(found.get().getGeo()).isNotNull();
        assertThat(found.get().getGeo().getLat()).isEqualTo("10.123");
    }
} 