package com.example.jsonplaceholder.core.integration;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.controller.UserController;
import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.dto.CompanyDto;
import com.example.jsonplaceholder.core.dto.GeoDto;
import com.example.jsonplaceholder.core.dto.UserDto;
import com.example.jsonplaceholder.core.service.UserService;
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

@WebMvcTest(controllers = {UserController.class, AuthenticationController.class})
@Import(TestSecurityConfig.class)
class UserControllerIntegrationTest extends AbstractAuthenticatedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    private UserDto userDto;
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
        
        // Create address
        GeoDto geoDto = new GeoDto(null, "10.123", "20.456");
        AddressDto addressDto = new AddressDto(null, "Main St", "Apt 1", "Testville", "12345", geoDto);
        
        // Create company
        CompanyDto companyDto = new CompanyDto(null, "Test Company", "Catch the best", "bs-value");
        
        // Create user
        userDto = new UserDto(null, "John", "Doe", null, "john@example.com", addressDto, "123-456-7890", "example.com", companyDto);
        
        // Mock service responses
        when(userService.create(any(UserDto.class))).thenReturn(userDto);
        when(userService.getById(any(Long.class))).thenReturn(Optional.of(userDto));
        when(userService.getAll()).thenReturn(List.of(userDto));
        when(userService.update(any(Long.class), any(UserDto.class))).thenReturn(Optional.of(userDto));
        when(userService.delete(any(Long.class))).thenReturn(true);
    }

    @Test
    @DisplayName("POST /api/v1/user should create and return user")
    void createUser_shouldReturnCreatedUser() throws Exception {
        String json = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/api/v1/user")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("123-456-7890"))
                .andExpect(jsonPath("$.website").value("example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/user should return all users")
    void getAllUsers_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/user")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    @DisplayName("GET /api/v1/user/{id} should return user if exists")
    void getUserById_shouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("PUT /api/v1/user/{id} should update and return user if exists")
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        userDto.setFirstName("Updated");
        userDto.setLastName("User");
        String updateJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(put("/api/v1/user/1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    @DisplayName("DELETE /api/v1/user/{id} should delete user and return 204")
    void deleteUser_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/user/1")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 