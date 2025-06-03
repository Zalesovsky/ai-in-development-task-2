package com.example.jsonplaceholder.core.repository;

import com.example.jsonplaceholder.core.model.Geo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GeoRepositoryTest extends AbstractPostgresContainerTest {

    @Autowired
    private GeoRepository geoRepository;

    @Test
    @DisplayName("Should save and retrieve Geo")
    void saveAndFindById_whenGeo_shouldPersistAndRetrieve() {
        Geo geo = new Geo();
        geo.setLat("50.123");
        geo.setLng("60.456");

        Geo saved = geoRepository.save(geo);
        Optional<Geo> found = geoRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getLat()).isEqualTo("50.123");
        assertThat(found.get().getLng()).isEqualTo("60.456");
    }
} 