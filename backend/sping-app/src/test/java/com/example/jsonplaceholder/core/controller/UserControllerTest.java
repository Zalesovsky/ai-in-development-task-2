package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.TestSecurityConfig;
import com.example.jsonplaceholder.core.dto.UserDto;
import com.example.jsonplaceholder.core.service.UserService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({JwtAuthenticationFilter.class, TestSecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/user should return all users")
    void getAll_whenUsersExist_shouldReturnListOfUserDtos() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john@example.com");
        given(userService.getAll()).willReturn(Arrays.asList(userDto));

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/user/{id} should return user if exists")
    void getById_whenUserExists_shouldReturnUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john@example.com");
        given(userService.getById(1L)).willReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/user/{id} should return 404 if not found")
    void getById_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
        given(userService.getById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/user should create and return user")
    void create_whenValidUserDto_shouldReturnCreatedUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john@example.com");
        given(userService.create(any(UserDto.class))).willReturn(userDto);

        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@example.com\"}";

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/user/{id} should update and return user if exists")
    void update_whenUserExists_shouldReturnUpdatedUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Updated");
        userDto.setLastName("Name");
        userDto.setEmail("updated@example.com");
        given(userService.update(eq(1L), any(UserDto.class))).willReturn(Optional.of(userDto));

        String json = "{\"firstName\":\"Updated\",\"lastName\":\"Name\",\"email\":\"updated@example.com\"}";

        mockMvc.perform(put("/api/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/user/{id} should return 404 if not found")
    void update_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
        given(userService.update(eq(1L), any(UserDto.class))).willReturn(Optional.empty());

        String json = "{\"firstName\":\"Updated\",\"lastName\":\"Name\",\"email\":\"updated@example.com\"}";

        mockMvc.perform(put("/api/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/user/{id} should return 204 if deleted")
    void delete_whenUserExists_shouldReturnNoContent() throws Exception {
        given(userService.delete(1L)).willReturn(true);

        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/user/{id} should return 404 if not found")
    void delete_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
        given(userService.delete(1L)).willReturn(false);

        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isNotFound());
    }

}