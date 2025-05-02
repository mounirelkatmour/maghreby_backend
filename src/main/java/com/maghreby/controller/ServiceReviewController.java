package com.maghreby.controller;

import com.maghreby.model.Review;
import com.maghreby.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services/{type}/{serviceId}/reviews")
public class ServiceReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review createReview(@PathVariable String type, @PathVariable String serviceId, @RequestBody Review review) {
        review.setOfferId(serviceId);
        return reviewService.createReview(review);
    }

    @GetMapping
    public List<Review> getReviews(@PathVariable String type, @PathVariable String serviceId) {
        return reviewService.getReviewsByOfferId(serviceId);
    }

    @GetMapping("/average")
    public double getAverageRating(@PathVariable String type, @PathVariable String serviceId) {
        return reviewService.calculateAverageRating(serviceId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable String type, @PathVariable String serviceId, @PathVariable String reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable String type, @PathVariable String serviceId, @PathVariable String reviewId, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String type, @PathVariable String serviceId, @PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
