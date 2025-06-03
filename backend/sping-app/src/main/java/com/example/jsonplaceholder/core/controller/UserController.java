package com.example.jsonplaceholder.core.controller;

import com.example.jsonplaceholder.core.dto.UserDto;
import com.example.jsonplaceholder.core.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Controller", description = "API for user management")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable @Positive Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create user", description = "Creates a new user")
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @Operation(summary = "Update user", description = "Updates user data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable @Positive Long id, @Valid @RequestBody UserDto userDto) {
        return userService.update(id, userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        if (userService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 