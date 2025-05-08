package com.maghreby.controller;

import com.maghreby.model.Accommodation;
import com.maghreby.service.AccommodationService;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Accommodation> getAllAccommodations(@RequestParam(required = false) String userId) {
        List<Accommodation> accommodations = accommodationService.getAllAccommodations();
        if (userId != null) {
            List<String> favoriteIds = favoriteService.getUserFavoritesByType(userId, "accommodations")
                .stream()
                .map(fav -> fav.getOfferId())
                .toList();
            for (Accommodation accommodation : accommodations) {
                accommodation.setFavorite(favoriteIds.contains(accommodation.getId()));
            }
        }
        return accommodations;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getAccommodationById(@PathVariable String id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        return accommodation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Accommodation createAccommodation(@RequestBody Accommodation accommodation) {
        return accommodationService.createAccommodation(accommodation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accommodation> updateAccommodation(@PathVariable String id, @RequestBody Accommodation accommodation) {
        Optional<Accommodation> updated = accommodationService.updateAccommodation(id, accommodation);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable String id) {
        boolean deleted = accommodationService.deleteAccommodation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
