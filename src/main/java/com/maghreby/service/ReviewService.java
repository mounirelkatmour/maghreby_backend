package com.maghreby.service;

import com.maghreby.model.Review;
import com.maghreby.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        review.setCreatedAt(new java.util.Date());
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByOfferId(String offerId) {
        return reviewRepository.findByOfferId(offerId);
    }

    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    public Review updateReview(String id, Review review) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setRating(review.getRating());
                    existingReview.setComment(review.getComment());
                    existingReview.setUpdatedAt(new java.util.Date());
                    return reviewRepository.save(existingReview);
                })
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    public double calculateAverageRating(String offerId) {
        List<Review> reviews = getReviewsByOfferId(offerId);
        if (reviews.isEmpty()) return 0.0;
        
        double sum = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        
        return sum / reviews.size();
    }

    public boolean hasUserReviewed(String offerId, String userId) {
        return reviewRepository.findByOfferIdAndUserId(offerId, userId).size() > 0;
    }
}
