package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.TypeAlias;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "offers")
@TypeAlias("Restaurant")
public class Restaurant extends Service {
    private String cuisineType;
    private String openingHours;
    private String closingHours;
    private double minPrice;
    private List<MenuItem> menu;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuItem {
        private String name;
        private String description;
        private double price;
    }
}
