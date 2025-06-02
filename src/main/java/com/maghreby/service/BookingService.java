package com.maghreby.service;

import com.maghreby.model.Booking;
import com.maghreby.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByServiceId(String serviceId) {
        return bookingRepository.findByServiceId(serviceId);
    }

    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public boolean isBookingOverlap(String serviceId, Date startDate, Date endDate) {
        List<Booking> overlaps = bookingRepository
            .findByServiceIdAndStatusNotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                serviceId, "CANCELLED", endDate, startDate);
        return !overlaps.isEmpty();
    }

    public Booking updateBooking(Booking booking) {
        // Find the existing booking
        Optional<Booking> existingOpt = bookingRepository.findById(booking.getId());
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        Booking existing = existingOpt.get();
        // Only update fields that are not null in the input booking
        if (booking.getStatus() != null) existing.setStatus(booking.getStatus());
        if (booking.getPaymentStatus() != null) existing.setPaymentStatus(booking.getPaymentStatus());
        if (booking.getPaymentMethod() != null) existing.setPaymentMethod(booking.getPaymentMethod());
        if (booking.getStartDate() != null) existing.setStartDate(booking.getStartDate());
        if (booking.getEndDate() != null) existing.setEndDate(booking.getEndDate());
        if (booking.getAmount() != 0) existing.setAmount(booking.getAmount());
        if (booking.getServiceId() != null) existing.setServiceId(booking.getServiceId());
        if (booking.getUserId() != null) existing.setUserId(booking.getUserId());
        if (booking.getCreatedAt() != null) existing.setCreatedAt(booking.getCreatedAt());
        if (booking.getUpdatedAt() != null) existing.setUpdatedAt(booking.getUpdatedAt());
        return bookingRepository.save(existing);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}
