package com.example.jsonplaceholder.core.integration;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.GeoController;
import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.service.GeoService;
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

@WebMvcTest(controllers = {GeoController.class, AuthenticationController.class})
@Import(TestSecurityConfig.class)
class GeoControllerIntegrationTest extends AbstractAuthenticatedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GeoService geoService;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    private GeoDto geoDto;
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
        geoDto = new GeoDto(null, "10.123", "20.456");
        
        // Mock service responses
        when(geoService.create(any(GeoDto.class))).thenReturn(geoDto);
        when(geoService.update(any(Long.class), any(GeoDto.class))).thenReturn(Optional.of(geoDto));
        when(geoService.getById(any(Long.class))).thenReturn(Optional.of(geoDto));
        when(geoService.getAll()).thenReturn(List.of(geoDto));
        when(geoService.delete(any(Long.class))).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/v1/geo should create and return geo")
    void createGeo_shouldReturnCreatedGeo() throws Exception {
        String json = objectMapper.writeValueAsString(geoDto);
        mockMvc.perform(post("/api/v1/geo")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value("10.123"))
                .andExpect(jsonPath("$.lng").value("20.456"));
    }

    @Test
    @DisplayName("GET /api/v1/geo should return all geos")
    void getAllGeos_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/geo")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lat").value("10.123"))
                .andExpect(jsonPath("$[0].lng").value("20.456"));
    }

    @Test
    @DisplayName("GET /api/v1/geo/{id} should return geo if exists")
    void getGeoById_shouldReturnGeo() throws Exception {
        mockMvc.perform(get("/api/v1/geo/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value("10.123"))
                .andExpect(jsonPath("$.lng").value("20.456"));
    }

    @Test
    @DisplayName("PUT /api/v1/geo/{id} should update and return geo if exists")
    void updateGeo_shouldReturnUpdatedGeo() throws Exception {
        String json = objectMapper.writeValueAsString(geoDto);
        mockMvc.perform(put("/api/v1/geo/1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value("10.123"))
                .andExpect(jsonPath("$.lng").value("20.456"));
    }

    @Test
    @DisplayName("DELETE /api/v1/geo/{id} should delete geo and return 204")
    void deleteGeo_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/geo/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }

}