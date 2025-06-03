package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.CompanyController;
import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.service.CompanyService;
import com.example.jsonplaceholder.security.config.JwtAuthenticationFilter;
import com.example.jsonplaceholder.security.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(CompanyController.class)
@Import({JwtAuthenticationFilter.class, TestSecurityConfig.class})
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/company should return all companies")
    void getAll_whenCompaniesExist_shouldReturnListOfCompanyDtos() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Test Company");
        companyDto.setCatchPhrase("Catch Phrase");
        companyDto.setBs("BS");
        given(companyService.getAll()).willReturn(Arrays.asList(companyDto));

        mockMvc.perform(get("/api/v1/company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Company"));
    }

    @Test
    @DisplayName("GET /api/v1/company/{id} should return company if exists")
    void getById_whenCompanyExists_shouldReturnCompanyDto() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Test Company");
        companyDto.setCatchPhrase("Catch Phrase");
        companyDto.setBs("BS");
        given(companyService.getById(1L)).willReturn(Optional.of(companyDto));

        mockMvc.perform(get("/api/v1/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @DisplayName("GET /api/v1/company/{id} should return 404 if not found")
    void getById_whenCompanyDoesNotExist_shouldReturnNotFound() throws Exception {
        given(companyService.getById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/company/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/company should create and return company")
    void create_whenValidCompanyDto_shouldReturnCreatedCompanyDto() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Test Company");
        companyDto.setCatchPhrase("Catch Phrase");
        companyDto.setBs("BS");
        given(companyService.create(any(CompanyDto.class))).willReturn(companyDto);

        String json = "{\"name\":\"Test Company\",\"catchPhrase\":\"Catch Phrase\",\"bs\":\"BS\"}";

        mockMvc.perform(post("/api/v1/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @DisplayName("PUT /api/v1/company/{id} should update and return company if exists")
    void update_whenCompanyExists_shouldReturnUpdatedCompanyDto() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Updated Company");
        companyDto.setCatchPhrase("Updated Catch Phrase");
        companyDto.setBs("Updated BS");
        given(companyService.update(eq(1L), any(CompanyDto.class))).willReturn(Optional.of(companyDto));

        String json = "{\"name\":\"Updated Company\",\"catchPhrase\":\"Updated Catch Phrase\",\"bs\":\"Updated BS\"}";

        mockMvc.perform(put("/api/v1/company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Company"));
    }

    @Test
    @DisplayName("PUT /api/v1/company/{id} should return 404 if not found")
    void update_whenCompanyDoesNotExist_shouldReturnNotFound() throws Exception {
        given(companyService.update(eq(1L), any(CompanyDto.class))).willReturn(Optional.empty());

        String json = "{\"name\":\"Updated Company\",\"catchPhrase\":\"Updated Catch Phrase\",\"bs\":\"Updated BS\"}";

        mockMvc.perform(put("/api/v1/company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/company/{id} should return 204 if deleted")
    void delete_whenCompanyExists_shouldReturnNoContent() throws Exception {
        given(companyService.delete(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/company/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/company/{id} should return 404 if not found")
    void delete_whenCompanyDoesNotExist_shouldReturnNotFound() throws Exception {
        given(companyService.delete(1L)).willReturn(false);

        mockMvc.perform(delete("/api/v1/company/1"))
                .andExpect(status().isNotFound());
    }
} 