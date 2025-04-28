package com.maghreby.repository;

import com.maghreby.model.ServiceProvider;
import com.maghreby.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    void save(ServiceProvider serviceProvider);
}