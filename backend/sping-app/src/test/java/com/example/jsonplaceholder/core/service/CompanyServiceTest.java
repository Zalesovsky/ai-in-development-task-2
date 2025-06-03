package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.mapper.CompanyMapper;
import com.example.jsonplaceholder.core.model.Company;
import com.example.jsonplaceholder.core.repository.CompanyRepository;
import com.example.jsonplaceholder.core.service.CompanyService;
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
class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyMapper companyMapper;
    @InjectMocks
    private CompanyService companyService;

    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    void setUp() {
        company = new Company();
        companyDto = new CompanyDto();
    }

    @Test
    void getAll_whenCompaniesExist_shouldReturnListOfCompanyDtos() {
        when(companyRepository.findAll()).thenReturn(Arrays.asList(company));
        when(companyMapper.toDto(company)).thenReturn(companyDto);
        List<CompanyDto> result = companyService.getAll();
        assertEquals(1, result.size());
        verify(companyRepository).findAll();
        verify(companyMapper).toDto(company);
    }

    @Test
    void getById_whenCompanyExists_shouldReturnCompanyDto() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyMapper.toDto(company)).thenReturn(companyDto);
        Optional<CompanyDto> result = companyService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(companyDto, result.get());
    }

    @Test
    void create_whenValidCompanyDto_shouldSaveAndReturnCompanyDto() {
        when(companyMapper.toEntity(companyDto)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDto(company)).thenReturn(companyDto);
        CompanyDto result = companyService.create(companyDto);
        assertEquals(companyDto, result);
    }

    @Test
    void update_whenCompanyExists_shouldUpdateAndReturnCompanyDto() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        doNothing().when(companyMapper).update(companyDto, company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDto(company)).thenReturn(companyDto);
        Optional<CompanyDto> result = companyService.update(1L, companyDto);
        assertTrue(result.isPresent());
        assertEquals(companyDto, result.get());
    }

    @Test
    void delete_whenCompanyExists_shouldReturnTrue() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);
        boolean result = companyService.delete(1L);
        assertTrue(result);
        verify(companyRepository).delete(company);
    }

    @Test
    void delete_whenCompanyDoesNotExist_shouldReturnFalse() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = companyService.delete(1L);
        assertFalse(result);
    }
} 