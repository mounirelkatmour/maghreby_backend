package com.maghreby.repository;

import com.maghreby.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {
    // Custom query methods can be added here
}
