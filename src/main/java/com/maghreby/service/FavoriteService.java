package com.maghreby.service;

import com.maghreby.model.Favorite;
import com.maghreby.repository.FavoriteRepository;
import com.maghreby.service.OfferService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class FavoriteService {
    @Autowired
    private OfferService offerService;
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite addFavorite(String userId, String offerId, String type) {
        if (favoriteRepository.existsByUserIdAndOfferId(userId, offerId)) {
            throw new RuntimeException("This item is already in your favorites");
        }
        
        // Increment favorites count
        updateFavoritesCount(offerId, 1);
        
        Favorite favorite = Favorite.builder()
                .userId(userId)
                .offerId(offerId)
                .type(type)
                .createdAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();
        
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(String userId, String offerId) {
        if (!favoriteRepository.existsByUserIdAndOfferId(userId, offerId)) {
            throw new RuntimeException("This item is not in your favorites");
        }
        
        // Decrement favorites count
        updateFavoritesCount(offerId, -1);
        
        // Get the favorite before deleting to set updatedAt
        Favorite favorite = favoriteRepository.findByUserIdAndOfferId(userId, offerId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        
        favorite.setUpdatedAt(new java.util.Date());
        favoriteRepository.save(favorite);
        
        favoriteRepository.deleteByUserIdAndOfferId(userId, offerId);
    }

    private void updateFavoritesCount(String offerId, int increment) {
        offerService.updateFavoritesCount(offerId, increment);
    }



    public List<Favorite> getUserFavorites(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public List<Favorite> getUserFavoritesByType(String userId, String type) {
        return favoriteRepository.findByUserIdAndType(userId, type);
    }

    public List<Favorite> getOfferFavorites(String offerId, String type) {
        if (type == null) {
            return favoriteRepository.findByOfferId(offerId);
        }
        return favoriteRepository.findByOfferIdAndType(offerId, type);
    }
}
