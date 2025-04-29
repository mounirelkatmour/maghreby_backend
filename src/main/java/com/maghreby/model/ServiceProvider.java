package com.maghreby.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("unused")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document(collection = "users")
@TypeAlias("ServiceProvider")
public class ServiceProvider extends User {

    private boolean isApproved;
    private ServiceType service; // No default NSP. Service provider should have a real service type.

    // Add explicit no-args constructor for ServiceProvider
    public ServiceProvider() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().name()));
    }
}
