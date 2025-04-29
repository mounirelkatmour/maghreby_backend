package com.maghreby.controller;

import com.maghreby.model.ServiceProviderRequest;
import com.maghreby.service.ServiceProviderRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-provider-requests")
@RequiredArgsConstructor
public class ServiceProviderRequestController {

    private final ServiceProviderRequestService serviceProviderRequestService;

    // Get all requests
    @GetMapping
    public List<ServiceProviderRequest> getAllRequests() {
        return serviceProviderRequestService.getAllRequests();
    }

    // Approve a service provider request
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveRequest(@PathVariable String id) {
        boolean approved = serviceProviderRequestService.approveRequest(id);
        if (approved) {
            return ResponseEntity.ok("Service provider request approved.");
        }
        return ResponseEntity.status(400).body("Failed to approve request.");
    }

    // Reject a service provider request
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectRequest(@PathVariable String id) {
        boolean rejected = serviceProviderRequestService.rejectRequest(id);
        if (rejected) {
            return ResponseEntity.ok("Service provider request rejected.");
        }
        return ResponseEntity.status(400).body("Failed to reject request.");
    }
}
