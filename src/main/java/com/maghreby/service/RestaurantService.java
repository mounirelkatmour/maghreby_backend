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
            updatedRestaurant.setId(id);
            return offerRepository.save(updatedRestaurant);
        });
    }

    public boolean deleteRestaurant(String id) {
        return getRestaurantById(id).map(restaurant -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
