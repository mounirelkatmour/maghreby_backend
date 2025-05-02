package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "offers")
@TypeAlias("Car")
public class Car extends Service {
    private String brand;
    private String model;
    private int year;
    private int seats;
    private Transmission transmission;
    private FuelType fuelType;
    private String licencePlate;
    private double pricePerDay;

    public enum Transmission {
        MANUAL, AUTOMATIC
    }

    public enum FuelType {
        DIESEL, GASOLINE, HYBRID, ELECTRIC
    }
}
