package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Service extends Offer {
    private String offerType;
    private ServiceLocation location;
    private String serviceProviderId;

    @JsonProperty("offerType")
    public String getOfferType() {
        return this.getClass().getSimpleName();
    }
}
