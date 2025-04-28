package com.maghreby.controller;

import com.maghreby.model.*;
import com.maghreby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
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

    // Create a new AdminUser
    @PostMapping("/admin")
    public ResponseEntity<User> createAdminUser(@RequestBody AdminUser adminUser) {
        User createdUser = userService.createUser(adminUser);
        return ResponseEntity.ok(createdUser);
    }

    // Create a new RegularUser
    @PostMapping("/regular")
    public ResponseEntity<User> createRegularUser(@RequestBody RegularUser regularUser) {
        User createdUser = userService.createUser(regularUser);
        return ResponseEntity.ok(createdUser);
    }
}
