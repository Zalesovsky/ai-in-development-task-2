package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.model.Company;
import com.example.jsonplaceholder.core.repository.CompanyRepository;
import com.example.jsonplaceholder.core.mapper.CompanyMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public List<CompanyDto> getAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CompanyDto> getById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDto);
    }

    public CompanyDto create(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(companyRepository.save(company));
    }

    public Optional<CompanyDto> update(Long id, CompanyDto companyDto) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    companyMapper.update(companyDto, existingCompany);
                    return companyMapper.toDto(companyRepository.save(existingCompany));
                });
    }

    public boolean delete(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    return true;
                })
                .orElse(false);
    }
} 