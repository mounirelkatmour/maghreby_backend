package com.maghreby.service;

import com.maghreby.model.Accommodation;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Accommodation> getAllAccommodations() {
        return offerRepository.findAll().stream()
                .filter(Accommodation.class::isInstance)
                .map(Accommodation.class::cast)
                .toList();
    }

    public Optional<Accommodation> getAccommodationById(String id) {
        return offerRepository.findById(id)
                .filter(Accommodation.class::isInstance)
                .map(Accommodation.class::cast);
    }

    public Accommodation createAccommodation(Accommodation accommodation) {
        return offerRepository.save(accommodation);
    }

    public Optional<Accommodation> updateAccommodation(String id, Accommodation updatedAccommodation) {
        return getAccommodationById(id).map(existing -> {
            // Accommodation-specific fields
            if (updatedAccommodation.getStars() != 0) existing.setStars(updatedAccommodation.getStars());
            if (updatedAccommodation.getType() != null) existing.setType(updatedAccommodation.getType());
            if (updatedAccommodation.getAmenities() != null) existing.setAmenities(updatedAccommodation.getAmenities());
            if (updatedAccommodation.getRooms() != null) existing.setRooms(updatedAccommodation.getRooms());
            // Service fields
            if (updatedAccommodation.getLocation() != null) existing.setLocation(updatedAccommodation.getLocation());
            if (updatedAccommodation.getServiceProviderId() != null) existing.setServiceProviderId(updatedAccommodation.getServiceProviderId());
            // Offer fields
            if (updatedAccommodation.getName() != null) existing.setName(updatedAccommodation.getName());
            if (updatedAccommodation.getDescription() != null) existing.setDescription(updatedAccommodation.getDescription());
            if (updatedAccommodation.getImages() != null) existing.setImages(updatedAccommodation.getImages());
            if (updatedAccommodation.getCreatedAt() != null) existing.setCreatedAt(updatedAccommodation.getCreatedAt());
            if (updatedAccommodation.getUpdatedAt() != null) existing.setUpdatedAt(updatedAccommodation.getUpdatedAt());
            existing.setFavorites(updatedAccommodation.getFavorites());
            existing.setAverageRating(updatedAccommodation.getAverageRating());
            return offerRepository.save(existing);
        });
    }

    public boolean deleteAccommodation(String id) {
        return getAccommodationById(id).map(accommodation -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
