package com.maghreby.controller;

import com.maghreby.dto.AuthRequest;
import com.maghreby.dto.AuthResponse;
import com.maghreby.dto.ForgotPasswordRequest;
import com.maghreby.dto.PasswordResetToken;
import com.maghreby.model.RegularUser;
import com.maghreby.dto.ResetPasswordRequest;
import com.maghreby.model.Role;
import com.maghreby.model.ServiceProviderRequest;
import com.maghreby.model.ServiceType;
import com.maghreby.repository.PasswordResetTokenRepository;
import com.maghreby.repository.ServiceProviderRequestRepository;
import com.maghreby.repository.UserRepository;
import com.maghreby.services.JwtService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final ServiceProviderRequestRepository serviceProviderRequestRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ServiceProviderRequest request) {
        String service = (request.getServiceType() == null) ? "NSP" : request.getServiceType().name();
        String email = request.getEmail();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Check if email is already used by a registered user
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Email is already in use. Please use a different email."));
        }
        // Optionally, check for pending service provider requests with the same email
        // (Uncomment if you want to prevent duplicate emails even in pending requests)
        if (serviceProviderRequestRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Email is already in use. Please use a different email."));
        }

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
            // Return JSON message
            return ResponseEntity.ok(Map.of("message", "Registration successful. You can now log in."));
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
            // Return JSON message
            return ResponseEntity.ok(Map.of("message", "Registration submitted, pending approval."));
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

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        if (!userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(404).body(Map.of("message", "No user found with this email."));
        }
        // Remove any previous reset tokens for this email
        passwordResetTokenRepository.deleteByEmail(email);
        // Generate token
        String token = UUID.randomUUID().toString();
        // Set expiry (e.g., 1 hour)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        PasswordResetToken resetToken = new PasswordResetToken(null, email, token, cal.getTime());
        passwordResetTokenRepository.save(resetToken);
        // Send email
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " + resetLink + "\nIf you did not request a password reset, please ignore this email.");
        mailSender.send(message);
        return ResponseEntity.ok(Map.of("message", "Password reset email sent. Please check your inbox."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or expired token."));
        }
        var userOpt = userRepository.findByEmail(resetToken.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found."));
        }
        var user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        // Remove the used token
        passwordResetTokenRepository.deleteByEmail(resetToken.getEmail());
        return ResponseEntity.ok(Map.of("message", "Password has been reset successfully."));
    }
}