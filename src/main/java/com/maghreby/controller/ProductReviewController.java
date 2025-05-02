package com.maghreby.controller;

import com.maghreby.model.Review;
import com.maghreby.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/{productId}/reviews")
public class ProductReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review createReview(@PathVariable String productId, @RequestBody Review review) {
        review.setOfferId(productId);
        return reviewService.createReview(review);
    }

    @GetMapping
    public List<Review> getReviews(@PathVariable String productId) {
        return reviewService.getReviewsByOfferId(productId);
    }

    @GetMapping("/average")
    public double getAverageRating(@PathVariable String productId) {
        return reviewService.calculateAverageRating(productId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable String reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable String reviewId, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
