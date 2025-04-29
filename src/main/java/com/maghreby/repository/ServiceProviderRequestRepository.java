package com.maghreby.repository;

import com.maghreby.model.ServiceProviderRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRequestRepository extends MongoRepository<ServiceProviderRequest, String> {
    // Custom query methods can be added if needed
    Optional<ServiceProviderRequest> findByEmail(String email);
}
