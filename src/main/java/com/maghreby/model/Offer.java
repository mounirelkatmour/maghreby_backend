package com.maghreby.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Offer {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> images;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
}
