package com.maghreby.controller;

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
}
