package com.maghreby.service;

import com.maghreby.model.Product;
import com.maghreby.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Product> getAllProducts() {
        return offerRepository.findAll().stream()
                .filter(Product.class::isInstance)
                .map(Product.class::cast)
                .toList();
    }

    public Optional<Product> getProductById(String id) {
        return offerRepository.findById(id)
                .filter(Product.class::isInstance)
                .map(Product.class::cast);
    }

    public Product createProduct(Product product) {
        return offerRepository.save(product);
    }

    public Optional<Product> updateProduct(String id, Product updatedProduct) {
        return getProductById(id).map(existing -> {
            updatedProduct.setId(id);
            return offerRepository.save(updatedProduct);
        });
    }

    public boolean deleteProduct(String id) {
        return getProductById(id).map(product -> {
            offerRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
