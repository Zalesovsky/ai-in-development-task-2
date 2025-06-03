package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.UserDto;
import com.example.jsonplaceholder.core.mapper.UserMapper;
import com.example.jsonplaceholder.core.model.User;
import com.example.jsonplaceholder.core.repository.UserRepository;
import com.example.jsonplaceholder.core.service.UserService;
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
class AuthUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        userDto = new UserDto();
    }

    @Test
    void getAll_whenUsersExist_shouldReturnListOfUserDtos() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        List<UserDto> result = userService.getAll();
        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toDto(user);
    }

    @Test
    void getById_whenUserExists_shouldReturnUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        Optional<UserDto> result = userService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void create_whenValidUserDto_shouldSaveAndReturnUserDto() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        UserDto result = userService.create(userDto);
        assertEquals(userDto, result);
    }

    @Test
    void update_whenUserExists_shouldUpdateAndReturnUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).update(userDto, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        Optional<UserDto> result = userService.update(1L, userDto);
        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void delete_whenUserExists_shouldReturnTrue() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        boolean result = userService.delete(1L);
        assertTrue(result);
        verify(userRepository).delete(user);
    }

    @Test
    void delete_whenUserDoesNotExist_shouldReturnFalse() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = userService.delete(1L);
        assertFalse(result);
    }
} 