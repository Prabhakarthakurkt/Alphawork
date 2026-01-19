package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.User;
import com.alphawork.backend.dto.AuthResponse;
import com.alphawork.backend.dto.LoginRequest;
import com.alphawork.backend.dto.RegisterRequest;
import com.alphawork.backend.dto.UserDTO;
import com.alphawork.backend.repository.UserRepository;
import com.alphawork.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user.getId().toString(), user.getEmail(), user.getRole().toString());

        UserDTO userDTO = mapToDTO(user);
        return AuthResponse.builder()
                .token(token)
                .user(userDTO)
                .message("Login successful")
                .build();
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String roleName = request.getRole() != null ? request.getRole() : "USER";
        User.UserRole role = User.UserRole.valueOf(roleName);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser.getId().toString(), savedUser.getEmail(), savedUser.getRole().toString());

        UserDTO userDTO = mapToDTO(savedUser);
        return AuthResponse.builder()
                .token(token)
                .user(userDTO)
                .message("Registration successful")
                .build();
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
