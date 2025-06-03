package com.example.jsonplaceholder.security.service;

import com.example.jsonplaceholder.security.dto.auth.AuthenticationRequest;
import com.example.jsonplaceholder.security.dto.auth.AuthenticationResponse;
import com.example.jsonplaceholder.security.dto.auth.RegisterRequest;
import com.example.jsonplaceholder.security.model.Role;
import com.example.jsonplaceholder.security.model.AuthUser;
import com.example.jsonplaceholder.security.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (authUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (authUserRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        AuthUser authUser = AuthUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        authUserRepository.save(authUser);

        var jwtToken = jwtService.generateToken(mapToUser(authUser));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        AuthUser authUser = authUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(mapToUser(authUser));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .build();
    }

    private User mapToUser(AuthUser authUser) {
        return new User(authUser.getUsername(),
                authUser.getPasswordHash(),
                List.of(() -> "ROLE_" + authUser.getRole().name()));
    }

}