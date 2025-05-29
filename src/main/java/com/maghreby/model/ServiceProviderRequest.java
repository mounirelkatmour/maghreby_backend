package com.maghreby.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "service_provider_requests")
public class ServiceProviderRequest {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String country;
    private String city;
    private String bio;
    private Date birthDate; // Ensure this field is properly mapped
    private String occupation;
    private String profilImg; // Ensure this field is properly mapped
    private String auth0Id; // Ensure this field is properly mapped
    private ServiceType serviceType; // Ensure this field is properly mapped
    private Date createdAt; // Ensure this field is properly mapped
    private RequestStatus status;
    private LanguagePreference languagePreference;

    public enum RequestStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
