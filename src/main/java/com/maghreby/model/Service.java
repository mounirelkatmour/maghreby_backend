package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Service extends Offer {
    private String location; // address, city, or coordinates
    private String serviceProviderId;
}
