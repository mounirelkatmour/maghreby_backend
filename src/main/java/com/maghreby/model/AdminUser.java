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

    public AdminUser() {
        super();
        this.service = ServiceType.NSP; // Ensure default value is set
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().name()));
    }

    // Admin-specific methods can be added here
    public boolean isSuperAdmin() {
        return getRole() == Role.SUPERADMIN;
    }

    public void validateRole() {
        if (getRole() != Role.ADMIN && getRole() != Role.SUPERADMIN) {
            throw new IllegalArgumentException("AdminUser must have ADMIN or SUPERADMIN role");
        }
    }
}