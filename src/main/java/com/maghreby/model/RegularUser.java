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

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document(collection = "users")
@TypeAlias("RegularUser")
public class RegularUser extends User {

    @Builder.Default
    private ServiceType service = ServiceType.NSP;

    public RegularUser() {
        super();
        this.service = ServiceType.NSP; // Ensure default value is set
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().name()));
    }
}