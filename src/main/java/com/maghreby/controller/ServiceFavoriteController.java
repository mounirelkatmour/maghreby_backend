package com.maghreby.controller;

import com.maghreby.model.Favorite;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services/{type}/{serviceId}/favorites")
public class ServiceFavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public Favorite addFavorite(@PathVariable String type, @PathVariable String serviceId, @RequestParam String userId) {
        return favoriteService.addFavorite(userId, serviceId, type);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@PathVariable String type, @PathVariable String serviceId, @RequestParam String userId) {
        favoriteService.removeFavorite(userId, serviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Favorite> getFavorites(@PathVariable String type, @PathVariable String serviceId) {
        return favoriteService.getOfferFavorites(serviceId, type);
    }

    @GetMapping("/user")
    public List<Favorite> getUserFavorites(@RequestParam String userId) {
        return favoriteService.getUserFavorites(userId);
    }
}
