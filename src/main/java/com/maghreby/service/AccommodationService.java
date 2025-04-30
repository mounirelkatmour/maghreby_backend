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
            updatedAccommodation.setId(id);
            return offerRepository.save(updatedAccommodation);
        });
    }

    public boolean deleteAccommodation(String id) {
        return getAccommodationById(id).map(accommodation -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
