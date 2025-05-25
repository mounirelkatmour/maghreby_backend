package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
@Document(collection = "users")
@TypeAlias("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User implements UserDetails {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String occupation;
    private Date birthDate;
    private String bio;
    private String auth0Id; // Store Auth0 user ID
    
    @Builder.Default
    private LanguagePreference languagePreference = LanguagePreference.ENGLISH;
    
    @Builder.Default
    private Date createdAt = new Date();
    
    @Builder.Default
    private String profilImg = "assets/images/default_user.png";

    @Builder.Default
    private Map<String, List<String>> interests = new HashMap<>();

    @Override 
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    private boolean active = true; // Add active status for user

    private Role role; // Add role to base User class

    @Override 
    public String getUsername() { 
        return email;  
    }
    
}