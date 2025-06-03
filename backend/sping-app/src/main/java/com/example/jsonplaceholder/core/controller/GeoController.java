package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Geo Controller", description = "API for geo management")
@RestController
@RequestMapping("/api/v1/geo")
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @Operation(summary = "Get all geos", description = "Returns a list of all geos")
    @GetMapping
    public List<GeoDto> getAll() {
        return geoService.getAll();
    }

    @Operation(summary = "Get geo by ID", description = "Returns a geo by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<GeoDto> getById(@PathVariable @Positive Long id) {
        return geoService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create geo", description = "Creates a new geo")
    @PostMapping
    public GeoDto create(@Valid @RequestBody GeoDto geoDto) {
        return geoService.create(geoDto);
    }

    @Operation(summary = "Update geo", description = "Updates geo data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<GeoDto> update(@PathVariable @Positive Long id, @Valid @RequestBody GeoDto geoDto) {
        return geoService.update(id, geoDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete geo", description = "Deletes a geo by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        if (geoService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 