package com.maghreby.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "favorites")
@TypeAlias("Favorite")
public class Favorite extends BaseDocument {
    private String userId;
    private String offerId;
    private String type; // product or service type (accommodations, cars, restaurants, activities)
}
