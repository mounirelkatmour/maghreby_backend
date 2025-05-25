package com.maghreby.controller;

import com.maghreby.dto.UserUpdateDTO;
import com.maghreby.model.*;
import com.maghreby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// @SuppressWarnings("unused")
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a user by their ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Get a user by their auth sub
    @GetMapping("/auth0/{auth0Id}")
    public ResponseEntity<User> findByAuth0Id(@PathVariable String auth0Id) {
        Optional<User> user = userService.findByAuth0Id(auth0Id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update user profile
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserUpdateDTO userUpdates) {
        User updatedUser = userService.updateUser(id, userUpdates);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Change user role
    @PatchMapping("/{id}/role")
    public ResponseEntity<User> changeUserRole(@PathVariable String id, @RequestBody Map<String, String> body) {
        String newRole = body.get("role");
        User user = userService.changeUserRole(id, newRole);
        return ResponseEntity.ok(user);
    }

    // Change password (authenticated user)
    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable String id, @RequestBody Map<String, String> body) {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        boolean result = userService.changePassword(id, oldPassword, newPassword);
        if (result) {
            return ResponseEntity.ok(Map.of("message", "Password changed successfully."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Old password is incorrect."));
        }
    }

    // Get current authenticated user
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    // Update user interests
    @PutMapping("/{id}/interests")
    public ResponseEntity<User> updateUserInterests(@PathVariable String id, @RequestBody Map<String, List<String>> interests) {
        User user = userService.updateUserInterests(id, interests);
        return ResponseEntity.ok(user);
    }

    // Deactivate user
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable String id) {
        User user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }

    // Activate user
    @PatchMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable String id) {
        User user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    // Update user language
    @PutMapping("/{id}/language")
    public ResponseEntity<?> updateLanguage(@PathVariable String id, @RequestBody Map<String, String> body) {
        String languageCode = body.get("language");
        User updated = userService.updateUserLanguage(id, languageCode);
        return ResponseEntity.ok(updated);
    }

}
