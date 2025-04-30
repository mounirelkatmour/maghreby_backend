package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.TypeAlias;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "offers")
@TypeAlias("Accommodation")
public class Accommodation extends Service {
    private int stars; // For hotels
    private AccommodationType type; // HOTEL, HOSTEL, VILLA, APARTMENT
    private List<String> amenities;
    private List<Room> rooms;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Room {
        private java.util.Date startDate;
        private java.util.Date endDate;
        private double pricePerNight;
        private int maxGuests;
    }

    public enum AccommodationType {
        HOTEL, HOSTEL, VILLA, APARTMENT
    }
}
