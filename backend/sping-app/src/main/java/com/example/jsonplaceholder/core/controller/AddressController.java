package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Address Controller", description = "API for address management")
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Get all addresses", description = "Returns a list of all addresses")
    @GetMapping
    public List<AddressDto> getAll() {
        return addressService.getAll();
    }

    @Operation(summary = "Get address by ID", description = "Returns an address by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable @Positive Long id) {
        return addressService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create address", description = "Creates a new address")
    @PostMapping
    public AddressDto create(@Valid @RequestBody AddressDto addressDto) {
        return addressService.create(addressDto);
    }

    @Operation(summary = "Update address", description = "Updates address data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(@PathVariable @Positive Long id, @Valid @RequestBody AddressDto addressDto) {
        return addressService.update(id, addressDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete address", description = "Deletes an address by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        if (addressService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 