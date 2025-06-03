package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Company Controller", description = "API for company management")
@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Get all companies", description = "Returns a list of all companies")
    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @Operation(summary = "Get company by ID", description = "Returns a company by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable @Positive Long id) {
        return companyService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create company", description = "Creates a new company")
    @PostMapping
    public CompanyDto create(@Valid @RequestBody CompanyDto companyDto) {
        return companyService.create(companyDto);
    }

    @Operation(summary = "Update company", description = "Updates company data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable @Positive Long id, @Valid @RequestBody CompanyDto companyDto) {
        return companyService.update(id, companyDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete company", description = "Deletes a company by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        if (companyService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 