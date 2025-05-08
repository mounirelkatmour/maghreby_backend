package com.maghreby.controller;

import com.maghreby.model.Activity;
import com.maghreby.service.ActivityService;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Activity> getAllActivities(@RequestParam(required = false) String userId) {
        List<Activity> activities = activityService.getAllActivities();
        if (userId != null) {
            List<String> favoriteIds = favoriteService.getUserFavoritesByType(userId, "activities")
                .stream()
                .map(fav -> fav.getOfferId())
                .toList();
            for (Activity activity : activities) {
                activity.setFavorite(favoriteIds.contains(activity.getId()));
            }
        }
        return activities;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable String id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable String id, @RequestBody Activity activity) {
        Optional<Activity> updated = activityService.updateActivity(id, activity);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
        boolean deleted = activityService.deleteActivity(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
