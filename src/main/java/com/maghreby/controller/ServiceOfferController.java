package com.maghreby.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maghreby.model.Service;
import com.maghreby.service.ServiceOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/services")
public class ServiceOfferController {
    @Autowired
    private ServiceOfferService serviceOfferService;

    @GetMapping
    public List<Service> getAllServices() {
        return serviceOfferService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable String id) {
        Optional<Service> serviceOpt = serviceOfferService.getServiceById(id);
        if (serviceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serviceOpt.get());
    }
}
