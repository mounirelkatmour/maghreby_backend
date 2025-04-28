package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document(collection = "users")
@TypeAlias("AdminUser")
public class AdminUser extends User {
    
    @Builder.Default
    private ServiceType service = ServiceType.NSP; // Admins aren't service providers
    
    private Role role; // Can be either ADMIN or SUPERADMIN

    public AdminUser() {
        super();
        this.service = ServiceType.NSP; // Ensure default value is set
        this.role = Role.ADMIN; // Default role for AdminUser
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    // Admin-specific methods can be added here
    public boolean isSuperAdmin() {
        return role == Role.SUPERADMIN;
    }

    public void validateRole() {
        if (role != Role.ADMIN && role != Role.SUPERADMIN) {
            throw new IllegalArgumentException("AdminUser must have ADMIN or SUPERADMIN role");
        }
    }
}