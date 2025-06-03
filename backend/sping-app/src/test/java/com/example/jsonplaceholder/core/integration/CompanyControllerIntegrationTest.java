package com.example.jsonplaceholder.core.integration;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.CompanyController;
import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.service.CompanyService;
import com.example.jsonplaceholder.security.controller.AuthenticationController;
import com.example.jsonplaceholder.security.dto.auth.AuthenticationResponse;
import com.example.jsonplaceholder.security.repository.AuthUserRepository;
import com.example.jsonplaceholder.security.service.AuthenticationService;
import com.example.jsonplaceholder.security.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {CompanyController.class, AuthenticationController.class})
@Import(TestSecurityConfig.class)
class CompanyControllerIntegrationTest extends AbstractAuthenticatedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    private CompanyDto companyDto;
    private String authToken;

    @BeforeEach
    void setUp() throws Exception {
        // Mock authentication service responses
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token("test-token")
                .username("testuser")
                .email("test@example.com")
                .build();
        when(authenticationService.register(any())).thenReturn(authResponse);
        when(authenticationService.authenticate(any())).thenReturn(authResponse);
        
        authToken = getAuthToken();
        companyDto = new CompanyDto(null, "Test Company", "Catch the best", "bs-value");
        
        // Mock service responses
        when(companyService.create(any(CompanyDto.class))).thenReturn(companyDto);
        when(companyService.getById(any(Long.class))).thenReturn(Optional.of(companyDto));
        when(companyService.getAll()).thenReturn(List.of(companyDto));
        when(companyService.update(any(Long.class), any(CompanyDto.class))).thenReturn(Optional.of(companyDto));
        when(companyService.delete(any(Long.class))).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/v1/company should create and return company")
    void createCompany_shouldReturnCreatedCompany() throws Exception {
        String json = objectMapper.writeValueAsString(companyDto);
        mockMvc.perform(post("/api/v1/company")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Company"))
                .andExpect(jsonPath("$.catchPhrase").value("Catch the best"))
                .andExpect(jsonPath("$.bs").value("bs-value"));
    }

    @Test
    @DisplayName("GET /api/v1/company should return all companies")
    void getAllCompanies_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/company")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Company"));
    }

    @Test
    @DisplayName("GET /api/v1/company/{id} should return company if exists")
    void getCompanyById_shouldReturnCompany() throws Exception {
        mockMvc.perform(get("/api/v1/company/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @DisplayName("PUT /api/v1/company/{id} should update and return company if exists")
    void updateCompany_shouldReturnUpdatedCompany() throws Exception {
        companyDto.setName("Updated Company");
        String updateJson = objectMapper.writeValueAsString(companyDto);

        mockMvc.perform(put("/api/v1/company/1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Company"));
    }

    @Test
    @DisplayName("DELETE /api/v1/company/{id} should delete company and return 204")
    void deleteCompany_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/company/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 