package com.maghreby.service;

import com.maghreby.model.Offer;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public void updateFavoritesCount(String offerId, int increment) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
        offer.setFavorites(offer.getFavorites() + increment);
        offerRepository.save(offer);
    }
}
