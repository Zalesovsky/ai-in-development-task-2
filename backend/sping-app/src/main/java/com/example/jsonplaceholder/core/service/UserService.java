package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.UserDto;
import com.example.jsonplaceholder.core.model.User;
import com.example.jsonplaceholder.core.repository.UserRepository;
import com.example.jsonplaceholder.core.mapper.UserMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor 
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public Optional<UserDto> update(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    userMapper.update(userDto, existingUser);
                    return userMapper.toDto(userRepository.save(existingUser));
                });
    }

    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
} 