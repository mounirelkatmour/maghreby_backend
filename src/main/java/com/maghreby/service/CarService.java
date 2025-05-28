package com.maghreby.service;

import com.maghreby.model.Car;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Car> getAllCars() {
        return offerRepository.findAll().stream()
                .filter(Car.class::isInstance)
                .map(Car.class::cast)
                .toList();
    }

    public Optional<Car> getCarById(String id) {
        return offerRepository.findById(id)
                .filter(Car.class::isInstance)
                .map(Car.class::cast);
    }

    public Car createCar(Car car) {
        return offerRepository.save(car);
    }

    public Optional<Car> updateCar(String id, Car updatedCar) {
        return getCarById(id).map(existing -> {
            // Only update fields that are present in the PATCH request
            if (updatedCar.getBrand() != null) existing.setBrand(updatedCar.getBrand());
            if (updatedCar.getModel() != null) existing.setModel(updatedCar.getModel());
            if (updatedCar.getYear() != 0) existing.setYear(updatedCar.getYear());
            if (updatedCar.getSeats() != 0) existing.setSeats(updatedCar.getSeats());
            if (updatedCar.getTransmission() != null) existing.setTransmission(updatedCar.getTransmission());
            if (updatedCar.getFuelType() != null) existing.setFuelType(updatedCar.getFuelType());
            if (updatedCar.getLicencePlate() != null) existing.setLicencePlate(updatedCar.getLicencePlate());
            if (updatedCar.getPricePerDay() != 0) existing.setPricePerDay(updatedCar.getPricePerDay());
            // Service fields
            if (updatedCar.getLocation() != null) existing.setLocation(updatedCar.getLocation());
            if (updatedCar.getServiceProviderId() != null) existing.setServiceProviderId(updatedCar.getServiceProviderId());
            // Offer fields
            if (updatedCar.getName() != null) existing.setName(updatedCar.getName());
            if (updatedCar.getDescription() != null) existing.setDescription(updatedCar.getDescription());
            if (updatedCar.getImages() != null) existing.setImages(updatedCar.getImages());
            if (updatedCar.getCreatedAt() != null) existing.setCreatedAt(updatedCar.getCreatedAt());
            if (updatedCar.getUpdatedAt() != null) existing.setUpdatedAt(updatedCar.getUpdatedAt());
            // For favorites and averageRating, allow 0 as a valid value, so always set
            existing.setFavorites(updatedCar.getFavorites());
            existing.setAverageRating(updatedCar.getAverageRating());
            // Save and return
            return offerRepository.save(existing);
        });
    }

    public boolean deleteCar(String id) {
        return getCarById(id).map(car -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
