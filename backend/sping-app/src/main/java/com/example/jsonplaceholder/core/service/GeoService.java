package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.model.Geo;
import com.example.jsonplaceholder.core.repository.GeoRepository;
import com.example.jsonplaceholder.core.mapper.GeoMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GeoService {
    private final GeoRepository geoRepository;
    private final GeoMapper geoMapper;

    public List<GeoDto> getAll() {
        return geoRepository.findAll().stream()
                .map(geoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<GeoDto> getById(Long id) {
        return geoRepository.findById(id).map(geoMapper::toDto);
    }

    public GeoDto create(GeoDto geoDto) {
        Geo geo = geoMapper.toEntity(geoDto);
        return geoMapper.toDto(geoRepository.save(geo));
    }

    public Optional<GeoDto> update(Long id, GeoDto geoDto) {
        return geoRepository.findById(id)
                .map(existingGeo -> {
                    geoMapper.update(geoDto, existingGeo);
                    return geoMapper.toDto(geoRepository.save(existingGeo));
                });
    }

    public boolean delete(Long id) {
        return geoRepository.findById(id)
                .map(geo -> {
                    geoRepository.delete(geo);
                    return true;
                })
                .orElse(false);
    }
} 