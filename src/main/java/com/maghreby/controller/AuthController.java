package com.maghreby.controller;

import com.maghreby.dto.AuthRequest;
import com.maghreby.dto.AuthResponse;
import com.maghreby.model.RegularUser;
import com.maghreby.model.ServiceProviderRequest;
import com.maghreby.model.ServiceType;
import com.maghreby.model.Role;
import com.maghreby.repository.ServiceProviderRequestRepository;
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
    private final ServiceProviderRequestRepository serviceProviderRequestRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ServiceProviderRequest request) {
        String service = (request.getServiceType() == null) ? "NSP" : request.getServiceType().name();
        String email = request.getEmail();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Check if email is already used by a registered user
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body("Email is already in use. Please use a different email.");
        }
        // Optionally, check for pending service provider requests with the same email
        // (Uncomment if you want to prevent duplicate emails even in pending requests)
        // if (serviceProviderRequestRepository.findByEmail(email).isPresent()) {
        //     return ResponseEntity.status(409).body("Email is already in use (pending approval). Please use a different email.");
        // }

        if ("NSP".equalsIgnoreCase(service)) {
            // Register as RegularUser
            var user = RegularUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(email)
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .country(request.getCountry())
                .languagePreference(request.getLanguagePreference())
                .service(ServiceType.NSP)
                .role(Role.USER)
                .build();
            userRepository.save(user);
            // No token returned on registration
            return ResponseEntity.ok("Registration successful. You can now log in.");
        } else {
            // Register as ServiceProviderRequest (pending approval)
            var serviceProviderRequest = ServiceProviderRequest.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(email)
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .country(request.getCountry())
                .serviceType(request.getServiceType())
                .createdAt(new java.util.Date())
                .status(ServiceProviderRequest.RequestStatus.PENDING)
                .build();
            serviceProviderRequestRepository.save(serviceProviderRequest);
            // No token returned on registration
            return ResponseEntity.ok("Registration submitted, pending approval.");
        }
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