package com.maghreby.service;

import com.maghreby.dto.UserUpdateDTO;
import com.maghreby.model.*;
import com.maghreby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public Optional<User> findByAuth0Id(String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id);
    }

    public User createUser(User user) {
        return userRepository.save(user); // Assuming you have a userRepository that extends MongoRepository<User, String>
    }

    public User updateUser(String id, UserUpdateDTO updates) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (updates.getFirstName() != null) existingUser.setFirstName(updates.getFirstName());
        if (updates.getLastName() != null) existingUser.setLastName(updates.getLastName());
        if (updates.getBio() != null) existingUser.setBio(updates.getBio());
        if (updates.getBirthDate() != null) {
            // If birthDate is a String, parse to Date
            if (updates.getBirthDate() instanceof String) {
                try {
                    existingUser.setBirthDate(java.sql.Date.valueOf(updates.getBirthDate()));
                } catch (Exception e) {
                    // handle parse error or ignore
                }
            }
        }
        if (updates.getCity() != null) existingUser.setCity(updates.getCity());
        if (updates.getCountry() != null) existingUser.setCountry(updates.getCountry());
        if (updates.getPhoneNumber() != null) existingUser.setPhoneNumber(updates.getPhoneNumber());
        if (updates.getOccupation() != null) existingUser.setOccupation(updates.getOccupation());
        if (updates.getEmail() != null) existingUser.setEmail(updates.getEmail());
        if (updates.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updates.getPassword()));
        }
        if (updates.getProfilImg() != null) existingUser.setProfilImg(updates.getProfilImg());

        return userRepository.save(existingUser);
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

    public User updateUserInterests(String id, Map<String, List<String>> interests) {
        User user = userRepository.findById(id).orElseThrow();
        user.setInterests(interests);
        // If user is RegularUser, set firstTimeLogin to false after onboarding
        if (user instanceof RegularUser) {
            ((RegularUser) user).setFirstTimeLogin(false);
        }
        return userRepository.save(user);
    }

    public User updateUserLanguage(String id, String languageCode) {
        User user = userRepository.findById(id).orElseThrow();
        for (LanguagePreference lang : LanguagePreference.values()) {
            if (lang.getCode().equals(languageCode)) {
                user.setLanguagePreference(lang);
                break;
            }
        }
        return userRepository.save(user);
    }
}