package com.maghreby.repository;

import com.maghreby.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByServiceId(String serviceId);
    List<Booking> findByUserId(String userId);
    // Find bookings for a service that overlap with a given date range
    List<Booking> findByServiceIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        String serviceId, Date endDate, Date startDate
    );
    // Find bookings for a service that overlap with a given date range and are not cancelled
    List<Booking> findByServiceIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        String serviceId, String status, Date endDate, Date startDate
    );
}
