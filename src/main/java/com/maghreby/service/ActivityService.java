package com.maghreby.service;

import com.maghreby.model.Activity;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Activity> getAllActivities() {
        return offerRepository.findAll().stream()
                .filter(Activity.class::isInstance)
                .map(Activity.class::cast)
                .toList();
    }

    public Optional<Activity> getActivityById(String id) {
        return offerRepository.findById(id)
                .filter(Activity.class::isInstance)
                .map(Activity.class::cast);
    }

    public Activity createActivity(Activity activity) {
        return offerRepository.save(activity);
    }

    public Optional<Activity> updateActivity(String id, Activity updatedActivity) {
        return getActivityById(id).map(existing -> {
            // Activity-specific fields
            if (updatedActivity.getDuration() != null) existing.setDuration(updatedActivity.getDuration());
            if (updatedActivity.getPrice() != 0) existing.setPrice(updatedActivity.getPrice());
            // Service fields
            if (updatedActivity.getLocation() != null) existing.setLocation(updatedActivity.getLocation());
            if (updatedActivity.getServiceProviderId() != null) existing.setServiceProviderId(updatedActivity.getServiceProviderId());
            // Offer fields
            if (updatedActivity.getName() != null) existing.setName(updatedActivity.getName());
            if (updatedActivity.getDescription() != null) existing.setDescription(updatedActivity.getDescription());
            if (updatedActivity.getImages() != null) existing.setImages(updatedActivity.getImages());
            if (updatedActivity.getCreatedAt() != null) existing.setCreatedAt(updatedActivity.getCreatedAt());
            if (updatedActivity.getUpdatedAt() != null) existing.setUpdatedAt(updatedActivity.getUpdatedAt());
            if (updatedActivity.isActive() != existing.isActive()) existing.setActive(updatedActivity.isActive());
            existing.setFavorites(updatedActivity.getFavorites());
            existing.setAverageRating(updatedActivity.getAverageRating());
            return offerRepository.save(existing);
        });
    }

    public boolean deleteActivity(String id) {
        return getActivityById(id).map(activity -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
