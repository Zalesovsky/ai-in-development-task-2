package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.AddressController;
import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.service.AddressService;
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

@WebMvcTest(AddressController.class)
@Import({JwtAuthenticationFilter.class, TestSecurityConfig.class})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/address should return all addresses")
    void getAll_whenAddressesExist_shouldReturnListOfAddressDtos() throws Exception {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("Main St");
        addressDto.setSuite("Suite 1");
        addressDto.setCity("New York");
        addressDto.setZipcode("10001");
        given(addressService.getAll()).willReturn(Arrays.asList(addressDto));

        mockMvc.perform(get("/api/v1/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].street").value("Main St"));
    }

    @Test
    @DisplayName("GET /api/v1/address/{id} should return address if exists")
    void getById_whenAddressExists_shouldReturnAddressDto() throws Exception {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("Main St");
        addressDto.setSuite("Suite 1");
        addressDto.setCity("New York");
        addressDto.setZipcode("10001");
        given(addressService.getById(1L)).willReturn(Optional.of(addressDto));

        mockMvc.perform(get("/api/v1/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.street").value("Main St"));
    }

    @Test
    @DisplayName("GET /api/v1/address/{id} should return 404 if not found")
    void getById_whenAddressDoesNotExist_shouldReturnNotFound() throws Exception {
        given(addressService.getById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/address/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/address should create and return address")
    void create_whenValidAddressDto_shouldReturnCreatedAddressDto() throws Exception {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("Main St");
        addressDto.setSuite("Suite 1");
        addressDto.setCity("New York");
        addressDto.setZipcode("10001");
        given(addressService.create(any(AddressDto.class))).willReturn(addressDto);

        String json = "{\"street\":\"Main St\",\"suite\":\"Suite 1\",\"city\":\"New York\",\"zipcode\":\"10001\"}";

        mockMvc.perform(post("/api/v1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.street").value("Main St"));
    }

    @Test
    @DisplayName("PUT /api/v1/address/{id} should update and return address if exists")
    void update_whenAddressExists_shouldReturnUpdatedAddressDto() throws Exception {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("Updated St");
        addressDto.setSuite("Suite 1");
        addressDto.setCity("New York");
        addressDto.setZipcode("10001");
        given(addressService.update(eq(1L), any(AddressDto.class))).willReturn(Optional.of(addressDto));

        String json = "{\"street\":\"Updated St\",\"suite\":\"Suite 1\",\"city\":\"New York\",\"zipcode\":\"10001\"}";

        mockMvc.perform(put("/api/v1/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.street").value("Updated St"));
    }

    @Test
    @DisplayName("PUT /api/v1/address/{id} should return 404 if not found")
    void update_whenAddressDoesNotExist_shouldReturnNotFound() throws Exception {
        given(addressService.update(eq(1L), any(AddressDto.class))).willReturn(Optional.empty());

        String json = "{\"street\":\"Updated St\",\"suite\":\"Suite 1\",\"city\":\"New York\",\"zipcode\":\"10001\"}";

        mockMvc.perform(put("/api/v1/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/address/{id} should return 204 if deleted")
    void delete_whenAddressExists_shouldReturnNoContent() throws Exception {
        given(addressService.delete(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/address/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/address/{id} should return 404 if not found")
    void delete_whenAddressDoesNotExist_shouldReturnNotFound() throws Exception {
        given(addressService.delete(1L)).willReturn(false);

        mockMvc.perform(delete("/api/v1/address/1"))
                .andExpect(status().isNotFound());
    }
} 