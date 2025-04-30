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
            updatedActivity.setId(id);
            return offerRepository.save(updatedActivity);
        });
    }

    public boolean deleteActivity(String id) {
        return getActivityById(id).map(activity -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
