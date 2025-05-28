package com.maghreby.controller;

import com.maghreby.model.Restaurant;
import com.maghreby.service.RestaurantService;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Restaurant> getAllRestaurants(@RequestParam(required = false) String userId) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (userId != null) {
            List<String> favoriteIds = favoriteService.getUserFavoritesByType(userId, "restaurants")
                .stream()
                .map(fav -> fav.getOfferId())
                .toList();
            for (Restaurant restaurant : restaurants) {
                restaurant.setFavorite(favoriteIds.contains(restaurant.getId()));
            }
        }
        return restaurants;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id,
                                                        @RequestParam(required = false) String userId) {
        Optional<Restaurant> restaurantOpt = restaurantService.getRestaurantById(id);
        if (restaurantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurant restaurant = restaurantOpt.get();
        if (userId != null) {
            boolean isFavorite = favoriteService.getUserFavoritesByType(userId, "restaurants")
                .stream()
                .map(fav -> fav.getOfferId())
                .anyMatch(favId -> favId.equals(restaurant.getId()));
            restaurant.setFavorite(isFavorite);
        }
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
        Optional<Restaurant> updated = restaurantService.updateRestaurant(id, restaurant);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        boolean deleted = restaurantService.deleteRestaurant(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
