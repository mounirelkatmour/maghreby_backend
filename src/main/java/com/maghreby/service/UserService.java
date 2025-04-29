package com.maghreby.service;

import com.maghreby.model.*;
import com.maghreby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user); // Assuming you have a userRepository that extends MongoRepository<User, String>
    }

    public User updateUser(String id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setCountry(updatedUser.getCountry());
        user.setLanguagePreference(updatedUser.getLanguagePreference());
        user.setProfilImg(updatedUser.getProfilImg());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User changeUserRole(String id, String newRole) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(Role.valueOf(newRole));
        return userRepository.save(user);
    }

    public boolean changePassword(String id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User deactivateUser(String id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        return userRepository.save(user);
    }

    public User activateUser(String id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(true);
        return userRepository.save(user);
    }
}