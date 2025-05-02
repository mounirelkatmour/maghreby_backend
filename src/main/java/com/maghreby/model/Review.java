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
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
@TypeAlias("Review")
public class Review extends BaseDocument {
    private String userId;
    private String offerId;
    private int rating; // 0-5
    private String comment;
}
