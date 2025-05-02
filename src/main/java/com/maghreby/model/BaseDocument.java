package com.maghreby.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@EqualsAndHashCode
@SuppressWarnings("unused")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDocument {
    @Id
    private String id;
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
