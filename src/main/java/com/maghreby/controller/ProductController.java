package com.maghreby.controller;

import com.maghreby.model.Product;
import com.maghreby.service.ProductService;
import com.maghreby.service.FavoriteService;
import com.maghreby.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String userId) {
        List<Product> products = productService.getAllProducts();
        if (userId != null) {
            List<String> favoriteIds = favoriteService.getUserFavoritesByType(userId, "products")
                .stream()
                .map(fav -> fav.getOfferId())
                .toList();
            for (Product product : products) {
                product.setFavorite(favoriteIds.contains(product.getId()));
            }
        }
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Optional<Product> updated = productService.updateProduct(id, product);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
