package com.maghreby.repository;

import com.maghreby.model.ServiceProviderRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRequestRepository extends MongoRepository<ServiceProviderRequest, String> {
    // Custom query methods can be added if needed
}
