package com.maghreby.service;

import com.maghreby.model.User;
import com.maghreby.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    public User updateUser(String id, User updatedUser) {
        // Check if the user exists
        Optional<User> existingUser = userRepository.findById(id);
        
        if(existingUser.isPresent()) {
            User user = existingUser.get();
            
            // Update user fields here, you can update any fields you want
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setCountry(updatedUser.getCountry());
            user.setLanguagePreference(updatedUser.getLanguagePreference());
            user.setProfilImg(updatedUser.getProfilImg());
            
            // Save updated user
            return userRepository.save(user);
        } else {
            // If user not found, we can return null or throw an exception
            return null; // Or you can throw a custom exception
        }
    }
    
}
