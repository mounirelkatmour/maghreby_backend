package com.maghreby.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .cors(cors -> cors.disable()) // Disable CORS for simplicity
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow access to all endpoints
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // Disable HTTP Basic authentication
            .formLogin(form -> form.disable()); // Disable the default login page

        return http.build();
    }
}
