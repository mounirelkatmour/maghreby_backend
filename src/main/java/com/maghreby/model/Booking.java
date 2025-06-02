package com.maghreby.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String serviceId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private double amount;
    private String paymentMethod; // e.g., "CASH", "CARD"
    private String paymentStatus; // e.g., "PENDING", "PAID", "FAILED"
    private String status; // e.g., "PENDING", "CONFIRMED", "CANCELLED"
    private Date createdAt;
    private Date updatedAt;
}
