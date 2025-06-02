package com.maghreby.controller;

import com.maghreby.model.Booking;
import com.maghreby.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        // Only check overlap for accommodation and car
        String offerType = booking.getServiceId() != null ? booking.getServiceId().toUpperCase() : "";
        boolean checkOverlap = false;
        if (offerType.contains("ACCOMMODATION") || offerType.contains("CAR")) {
            checkOverlap = true;
        }
        if (checkOverlap && bookingService.isBookingOverlap(booking.getServiceId(), booking.getStartDate(), booking.getEndDate())) {
            return ResponseEntity.badRequest().body("Booking overlaps with an existing booking.");
        }
        Booking created = bookingService.createBooking(booking);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable String id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/service/{serviceId}")
    public List<Booking> getBookingsByService(@PathVariable String serviceId) {
        return bookingService.getBookingsByServiceId(serviceId);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        Optional<Booking> existing = bookingService.getBookingById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        booking.setId(id);
        Booking updated = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
