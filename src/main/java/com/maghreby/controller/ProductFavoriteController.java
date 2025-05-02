package com.maghreby.controller;

import com.maghreby.model.Favorite;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/favorites")
public class ProductFavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public Favorite addFavorite(@PathVariable String productId, @RequestParam String userId) {
        return favoriteService.addFavorite(userId, productId, "product");
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@PathVariable String productId, @RequestParam String userId) {
        favoriteService.removeFavorite(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Favorite> getFavorites(@PathVariable String productId) {
        return favoriteService.getOfferFavorites(productId, "product");
    }

    @GetMapping("/user")
    public List<Favorite> getUserFavorites(@RequestParam String userId) {
        return favoriteService.getUserFavorites(userId);
    }
}
