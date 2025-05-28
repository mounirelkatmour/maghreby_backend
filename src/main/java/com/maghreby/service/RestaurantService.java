package com.maghreby.service;

import com.maghreby.model.Restaurant;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Restaurant> getAllRestaurants() {
        return offerRepository.findAll().stream()
                .filter(Restaurant.class::isInstance)
                .map(Restaurant.class::cast)
                .toList();
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return offerRepository.findById(id)
                .filter(Restaurant.class::isInstance)
                .map(Restaurant.class::cast);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return offerRepository.save(restaurant);
    }

    public Optional<Restaurant> updateRestaurant(String id, Restaurant updatedRestaurant) {
        return getRestaurantById(id).map(existing -> {
            // Restaurant-specific fields
            if (updatedRestaurant.getCuisineType() != null) existing.setCuisineType(updatedRestaurant.getCuisineType());
            if (updatedRestaurant.getOpeningHours() != null) existing.setOpeningHours(updatedRestaurant.getOpeningHours());
            if (updatedRestaurant.getClosingHours() != null) existing.setClosingHours(updatedRestaurant.getClosingHours());
            if (updatedRestaurant.getMinPrice() != 0) existing.setMinPrice(updatedRestaurant.getMinPrice());
            if (updatedRestaurant.getMenu() != null) existing.setMenu(updatedRestaurant.getMenu());
            // Service fields
            if (updatedRestaurant.getLocation() != null) existing.setLocation(updatedRestaurant.getLocation());
            if (updatedRestaurant.getServiceProviderId() != null) existing.setServiceProviderId(updatedRestaurant.getServiceProviderId());
            // Offer fields
            if (updatedRestaurant.getName() != null) existing.setName(updatedRestaurant.getName());
            if (updatedRestaurant.getDescription() != null) existing.setDescription(updatedRestaurant.getDescription());
            if (updatedRestaurant.getImages() != null) existing.setImages(updatedRestaurant.getImages());
            if (updatedRestaurant.getCreatedAt() != null) existing.setCreatedAt(updatedRestaurant.getCreatedAt());
            if (updatedRestaurant.getUpdatedAt() != null) existing.setUpdatedAt(updatedRestaurant.getUpdatedAt());
            existing.setFavorites(updatedRestaurant.getFavorites());
            existing.setAverageRating(updatedRestaurant.getAverageRating());
            return offerRepository.save(existing);
        });
    }

    public boolean deleteRestaurant(String id) {
        return getRestaurantById(id).map(restaurant -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
