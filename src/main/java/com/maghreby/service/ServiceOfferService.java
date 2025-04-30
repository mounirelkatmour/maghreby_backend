package com.maghreby.service;

import com.maghreby.model.Service;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceOfferService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Service> getAllServices() {
        return offerRepository.findAll().stream()
                .filter(Service.class::isInstance)
                .map(Service.class::cast)
                .toList();
    }

    public Optional<Service> getServiceById(String id) {
        return offerRepository.findById(id)
                .filter(Service.class::isInstance)
                .map(Service.class::cast);
    }

    public Service createService(Service service) {
        return offerRepository.save(service);
    }

    public Optional<Service> updateService(String id, Service updatedService) {
        return getServiceById(id).map(existing -> {
            updatedService.setId(id);
            return offerRepository.save(updatedService);
        });
    }

    public boolean deleteService(String id) {
        return getServiceById(id).map(service -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
