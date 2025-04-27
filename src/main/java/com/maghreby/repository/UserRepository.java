package com.maghreby.repository;

import com.maghreby.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // You can add custom queries later if needed!
}
