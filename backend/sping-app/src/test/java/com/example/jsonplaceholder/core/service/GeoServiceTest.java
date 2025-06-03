package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.mapper.GeoMapper;
import com.example.jsonplaceholder.core.model.Geo;
import com.example.jsonplaceholder.core.repository.GeoRepository;
import com.example.jsonplaceholder.core.service.GeoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoServiceTest {
    @Mock
    private GeoRepository geoRepository;
    @Mock
    private GeoMapper geoMapper;
    @InjectMocks
    private GeoService geoService;

    private Geo geo;
    private GeoDto geoDto;

    @BeforeEach
    void setUp() {
        geo = new Geo();
        geoDto = new GeoDto();
    }

    @Test
    void getAll_whenGeosExist_shouldReturnListOfGeoDtos() {
        when(geoRepository.findAll()).thenReturn(Arrays.asList(geo));
        when(geoMapper.toDto(geo)).thenReturn(geoDto);
        List<GeoDto> result = geoService.getAll();
        assertEquals(1, result.size());
        verify(geoRepository).findAll();
        verify(geoMapper).toDto(geo);
    }

    @Test
    void getById_whenGeoExists_shouldReturnGeoDto() {
        when(geoRepository.findById(1L)).thenReturn(Optional.of(geo));
        when(geoMapper.toDto(geo)).thenReturn(geoDto);
        Optional<GeoDto> result = geoService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(geoDto, result.get());
    }

    @Test
    void create_whenValidGeoDto_shouldSaveAndReturnGeoDto() {
        when(geoMapper.toEntity(geoDto)).thenReturn(geo);
        when(geoRepository.save(geo)).thenReturn(geo);
        when(geoMapper.toDto(geo)).thenReturn(geoDto);
        GeoDto result = geoService.create(geoDto);
        assertEquals(geoDto, result);
    }

    @Test
    void update_whenGeoExists_shouldUpdateAndReturnGeoDto() {
        when(geoRepository.findById(1L)).thenReturn(Optional.of(geo));
        doNothing().when(geoMapper).update(geoDto, geo);
        when(geoRepository.save(geo)).thenReturn(geo);
        when(geoMapper.toDto(geo)).thenReturn(geoDto);
        Optional<GeoDto> result = geoService.update(1L, geoDto);
        assertTrue(result.isPresent());
        assertEquals(geoDto, result.get());
    }

    @Test
    void delete_whenGeoExists_shouldReturnTrue() {
        when(geoRepository.findById(1L)).thenReturn(Optional.of(geo));
        doNothing().when(geoRepository).delete(geo);
        boolean result = geoService.delete(1L);
        assertTrue(result);
        verify(geoRepository).delete(geo);
    }

    @Test
    void delete_whenGeoDoesNotExist_shouldReturnFalse() {
        when(geoRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = geoService.delete(1L);
        assertFalse(result);
    }
} 