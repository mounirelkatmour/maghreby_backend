package com.maghreby.controller;

import com.maghreby.dto.AuthRequest;
import com.maghreby.dto.AuthResponse;
import com.maghreby.dto.RegisterRequest;
import com.maghreby.model.ServiceProvider;
import com.maghreby.model.Role;
import com.maghreby.repository.UserRepository;
import com.maghreby.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var user = ServiceProvider.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new java.util.HashMap<>(), user);
        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(new java.util.HashMap<>(), user);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .build());
    }
}