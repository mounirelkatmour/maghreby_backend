package com.maghreby.controller;

import com.maghreby.model.Car;
import com.maghreby.service.CarService;
import com.maghreby.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Car> getAllCars(@RequestParam(required = false) String userId) {
        List<Car> cars = carService.getAllCars();
        if (userId != null) {
            List<String> favoriteIds = favoriteService.getUserFavoritesByType(userId, "cars")
                .stream()
                .map(fav -> fav.getOfferId())
                .toList();
            for (Car car : cars) {
                car.setFavorite(favoriteIds.contains(car.getId()));
            }
        }
        return cars;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable String id, @RequestBody Car car) {
        Optional<Car> updated = carService.updateCar(id, car);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        boolean deleted = carService.deleteCar(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
