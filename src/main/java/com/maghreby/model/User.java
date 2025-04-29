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
    
    @Builder.Default
    private LanguagePreference languagePreference = LanguagePreference.ENGLISH;
    
    @Builder.Default
    private Date createdAt = new Date();
    
    @Builder.Default
    private String profilImg = "D:\\ELKATMOUR MOUNIR\\Stage 2eme annee\\Assets\\DEFAULT_USER_IMG.jpg";

    @Override 
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    
    @Override 
    public String getUsername() { 
        return email;  
    }
    
}