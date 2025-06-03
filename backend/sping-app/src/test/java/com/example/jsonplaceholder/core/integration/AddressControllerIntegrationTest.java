package com.example.jsonplaceholder.core.integration;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.AddressController;
import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.service.AddressService;
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

@WebMvcTest(controllers = {AddressController.class, AuthenticationController.class})
@Import(TestSecurityConfig.class)
class AddressControllerIntegrationTest extends AbstractAuthenticatedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    private AddressDto addressDto;
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
        
        GeoDto geoDto = new GeoDto(null, "10.123", "20.456");
        addressDto = new AddressDto(null, "Main St", "Apt 1", "Testville", "12345", geoDto);
        
        // Mock service responses
        when(addressService.create(any(AddressDto.class))).thenReturn(addressDto);
        when(addressService.getById(any(Long.class))).thenReturn(Optional.of(addressDto));
        when(addressService.getAll()).thenReturn(List.of(addressDto));
        when(addressService.update(any(Long.class), any(AddressDto.class))).thenReturn(Optional.of(addressDto));
        when(addressService.delete(any(Long.class))).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/v1/address should create and return address")
    void createAddress_shouldReturnCreatedAddress() throws Exception {
        String json = objectMapper.writeValueAsString(addressDto);
        mockMvc.perform(post("/api/v1/address")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"))
                .andExpect(jsonPath("$.suite").value("Apt 1"))
                .andExpect(jsonPath("$.city").value("Testville"))
                .andExpect(jsonPath("$.zipcode").value("12345"));
    }

    @Test
    @DisplayName("GET /api/v1/address should return all addresses")
    void getAllAddresses_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/address")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].street").value("Main St"));
    }

    @Test
    @DisplayName("GET /api/v1/address/{id} should return address if exists")
    void getAddressById_shouldReturnAddress() throws Exception {
        mockMvc.perform(get("/api/v1/address/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"));
    }

    @Test
    @DisplayName("PUT /api/v1/address/{id} should update and return address if exists")
    void updateAddress_shouldReturnUpdatedAddress() throws Exception {
        addressDto.setStreet("Updated St");
        addressDto.setSuite("Updated Suite");
        addressDto.setCity("Updated City");
        addressDto.setZipcode("54321");
        String updateJson = objectMapper.writeValueAsString(addressDto);

        mockMvc.perform(put("/api/v1/address/1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Updated St"));
    }

    @Test
    @DisplayName("DELETE /api/v1/address/{id} should delete address and return 204")
    void deleteAddress_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/address/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 