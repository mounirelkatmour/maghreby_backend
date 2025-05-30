package com.maghreby.service;

import com.maghreby.model.Role;
import com.maghreby.model.ServiceProvider;
import com.maghreby.model.ServiceProviderRequest;
import com.maghreby.repository.ServiceProviderRequestRepository;
import com.maghreby.repository.UserRepository;  // Assuming UserRepository is where we store users
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceProviderRequestService {

    // Repositories for interacting with the database
    private final ServiceProviderRequestRepository serviceProviderRequestRepository;
    private final UserRepository userRepository;  // To save approved service provider

    // Create a new service provider request
    public ServiceProviderRequest createRequest(ServiceProviderRequest request) {
        request.setCreatedAt(new Date()); // Set the current date and time
        request.setStatus(ServiceProviderRequest.RequestStatus.PENDING); // Set default status
        return serviceProviderRequestRepository.save(request);
    }

    // Approve a service provider request
    public boolean approveRequest(String id) {
        // Retrieve the request by its ID
        ServiceProviderRequest request = serviceProviderRequestRepository.findById(id).orElse(null);
        // Check if the request exists and is in PENDING status
        if (request != null && request.getStatus() == ServiceProviderRequest.RequestStatus.PENDING) {
            System.out.println("Request ID: " + request.getFirstName());
            // Update the status to APPROVED
            request.setStatus(ServiceProviderRequest.RequestStatus.APPROVED);
            System.out.println("Request approved: " + request.getId());
            // Save the updated request to the database
            serviceProviderRequestRepository.save(request);
            System.out.println("Request saved: " + request.getId());
            // Create a new ServiceProvider account based on the approved request
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.setFirstName(request.getFirstName());
            serviceProvider.setLastName(request.getLastName());
            serviceProvider.setEmail(request.getEmail());
            serviceProvider.setPassword(request.getPassword());  // Set the password from the request
            serviceProvider.setPhoneNumber(request.getPhoneNumber());
            serviceProvider.setCountry(request.getCountry());
            serviceProvider.setService(request.getServiceType());
            serviceProvider.setCity(request.getCity());
            serviceProvider.setBio(request.getBio());
            serviceProvider.setBirthDate(request.getBirthDate());
            serviceProvider.setOccupation(request.getOccupation());
            serviceProvider.setProfilImg(request.getProfilImg() != null && !request.getProfilImg().isEmpty() ? request.getProfilImg() : "D:\\ELKATMOUR MOUNIR\\Stage 2eme annee\\Assets\\DEFAULT_USER_IMG.jpg");
            serviceProvider.setLanguagePreference(request.getLanguagePreference());
            serviceProvider.setRole(Role.SERVICE_PROVIDER);  // Assign the SERVICE_PROVIDER role
            serviceProvider.setApproved(true);  // Mark the account as approved

            // Save the new ServiceProvider account to the database
            userRepository.save(serviceProvider);
            System.out.println("Service provider account created successfully.");
            return true;  // Indicate that the approval was successful
        }
        return false;  // Indicate that the approval failed
    }

    // Reject a service provider request
    public boolean rejectRequest(String id) {
        // Retrieve the request by its ID
        ServiceProviderRequest request = serviceProviderRequestRepository.findById(id).orElse(null);
        // Check if the request exists and is in PENDING status
        if (request != null && request.getStatus() == ServiceProviderRequest.RequestStatus.PENDING) {
            // Update the status to REJECTED
            request.setStatus(ServiceProviderRequest.RequestStatus.REJECTED);
            // Save the updated request to the database
            serviceProviderRequestRepository.save(request);
            return true;  // Indicate that the rejection was successful
        }
        return false;  // Indicate that the rejection failed
    }

    // Retrieve all service provider requests
    public List<ServiceProviderRequest> getAllRequests() {
        // Fetch all requests from the database and return them
        return serviceProviderRequestRepository.findAll();
    }
}
