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
            updatedCar.setId(id);
            return offerRepository.save(updatedCar);
        });
    }

    public boolean deleteCar(String id) {
        return getCarById(id).map(car -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
