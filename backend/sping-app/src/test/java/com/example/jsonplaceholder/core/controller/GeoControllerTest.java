package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.GeoController;
import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.service.GeoService;
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

@WebMvcTest(GeoController.class)
@Import({JwtAuthenticationFilter.class, TestSecurityConfig.class})
class GeoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoService geoService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/geo should return all geos")
    void getAll_whenGeosExist_shouldReturnListOfGeoDtos() throws Exception {
        GeoDto geoDto = new GeoDto();
        geoDto.setId(1L);
        geoDto.setLat("10.123");
        geoDto.setLng("20.456");
        given(geoService.getAll()).willReturn(Arrays.asList(geoDto));

        mockMvc.perform(get("/api/v1/geo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].lat").value("10.123"));
    }

    @Test
    @DisplayName("GET /api/v1/geo/{id} should return geo if exists")
    void getById_whenGeoExists_shouldReturnGeoDto() throws Exception {
        GeoDto geoDto = new GeoDto();
        geoDto.setId(1L);
        geoDto.setLat("10.123");
        geoDto.setLng("20.456");
        given(geoService.getById(1L)).willReturn(Optional.of(geoDto));

        mockMvc.perform(get("/api/v1/geo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lat").value("10.123"));
    }

    @Test
    @DisplayName("GET /api/v1/geo/{id} should return 404 if not found")
    void getById_whenGeoDoesNotExist_shouldReturnNotFound() throws Exception {
        given(geoService.getById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/geo/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/geo should create and return geo")
    void create_whenValidGeoDto_shouldReturnCreatedGeoDto() throws Exception {
        GeoDto geoDto = new GeoDto();
        geoDto.setId(1L);
        geoDto.setLat("10.123");
        geoDto.setLng("20.456");
        given(geoService.create(any(GeoDto.class))).willReturn(geoDto);

        String json = "{\"lat\":\"10.123\",\"lng\":\"20.456\"}";

        mockMvc.perform(post("/api/v1/geo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lat").value("10.123"));
    }

    @Test
    @DisplayName("PUT /api/v1/geo/{id} should update and return geo if exists")
    void update_whenGeoExists_shouldReturnUpdatedGeoDto() throws Exception {
        GeoDto geoDto = new GeoDto();
        geoDto.setId(1L);
        geoDto.setLat("20.456");
        geoDto.setLng("30.789");
        given(geoService.update(eq(1L), any(GeoDto.class))).willReturn(Optional.of(geoDto));

        String json = "{\"lat\":\"20.456\",\"lng\":\"30.789\"}";

        mockMvc.perform(put("/api/v1/geo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lat").value("20.456"));
    }

    @Test
    @DisplayName("PUT /api/v1/geo/{id} should return 404 if not found")
    void update_whenGeoDoesNotExist_shouldReturnNotFound() throws Exception {
        given(geoService.update(eq(1L), any(GeoDto.class))).willReturn(Optional.empty());

        String json = "{\"lat\":\"20.456\",\"lng\":\"30.789\"}";

        mockMvc.perform(put("/api/v1/geo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/geo/{id} should return 204 if deleted")
    void delete_whenGeoExists_shouldReturnNoContent() throws Exception {
        given(geoService.delete(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/geo/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/geo/{id} should return 404 if not found")
    void delete_whenGeoDoesNotExist_shouldReturnNotFound() throws Exception {
        given(geoService.delete(1L)).willReturn(false);

        mockMvc.perform(delete("/api/v1/geo/1"))
                .andExpect(status().isNotFound());
    }
} 