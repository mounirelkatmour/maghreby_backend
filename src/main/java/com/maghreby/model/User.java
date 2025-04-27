package com.maghreby.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    @Builder.Default
    private Role role = Role.USER;

    private String country;

    @Builder.Default
    private LanguagePreference languagePreference = LanguagePreference.ENGLISH;

    @Builder.Default
    private Date createdAt = new Date();

    @Builder.Default
    private ServiceType service = ServiceType.CUSTOMER;

    private String profilImg;
}
