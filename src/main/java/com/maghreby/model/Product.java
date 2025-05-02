package com.maghreby.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@SuppressWarnings("unused")
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "offers")
@TypeAlias("Product")
public class Product extends Offer {
    private int stock;
    private String category;
    private String adminId;
    private double price;
}
