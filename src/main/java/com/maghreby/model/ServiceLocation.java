package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceLocation {
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
}