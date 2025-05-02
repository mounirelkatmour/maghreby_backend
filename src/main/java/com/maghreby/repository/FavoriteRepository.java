package com.maghreby.repository;

import com.maghreby.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    List<Favorite> findByUserId(String userId);
    List<Favorite> findByOfferId(String offerId);
    List<Favorite> findByOfferIdAndType(String offerId, String type);
    List<Favorite> findByUserIdAndType(String userId, String type);
    boolean existsByUserIdAndOfferId(String userId, String offerId);
    void deleteByUserIdAndOfferId(String userId, String offerId);
    Optional<Favorite> findByUserIdAndOfferId(String userId, String offerId);
}
