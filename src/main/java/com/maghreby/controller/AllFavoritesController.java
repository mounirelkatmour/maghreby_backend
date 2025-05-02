package com.maghreby.controller;

import com.maghreby.model.Favorite;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class AllFavoritesController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/user")
    public List<Favorite> getUserFavorites(@RequestParam String userId, @RequestParam(required = false) String type) {
        if (type == null || type.equalsIgnoreCase("all")) {
            return favoriteService.getUserFavorites(userId);
        }
        return favoriteService.getUserFavoritesByType(userId, type);
    }

    @GetMapping("/offer/{offerId}")
    public List<Favorite> getAllOfferFavorites(@PathVariable String offerId) {
        return favoriteService.getOfferFavorites(offerId, null);
    }
}
