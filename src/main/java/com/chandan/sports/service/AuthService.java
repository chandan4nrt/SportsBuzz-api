package com.chandan.sports.service;

import com.chandan.sports.dto.response.AuthResponse;
import com.chandan.sports.config.JwtService;
import com.chandan.sports.dto.response.AuthResponse;
import com.chandan.sports.dto.request.RegisterRequest;
import com.chandan.sports.entity.Role;
import com.chandan.sports.entity.User;
import com.chandan.sports.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthResponse authResponse;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager, AuthResponse authResponse) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authResponse = authResponse;
    }

    public AuthResponse register(RegisterRequest request) {
        // Basic check to see if email is already taken
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password())); // Encrypting the password
        user.setRole(Role.USER); // Defaulting new sign-ups to USER role

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(RegisterRequest request) {
        // This line triggers Spring Security's deep verification checks
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        // If authentication didn't fail, fetch the user record and issue a token
        com.chandan.sports.entity.User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthResponse(jwtToken);
    }
}