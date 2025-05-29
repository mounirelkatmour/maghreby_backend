package com.maghreby.repository;

import com.maghreby.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    @SuppressWarnings("null")
    List<Review> findAll();
    List<Review> findByOfferId(String offerId);
    List<Review> findByUserId(String userId);
    List<Review> findByOfferIdAndUserId(String offerId, String userId);
}
