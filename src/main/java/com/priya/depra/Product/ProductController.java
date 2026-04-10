package com.priya.depra.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // =========================
    // PUBLIC ENDPOINTS
    // =========================

    @GetMapping
    public ResponseEntity<List<Productdto>> getAllActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productdto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Productdto>> getByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    @GetMapping("/price")
    public ResponseEntity<List<Productdto>> getByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        return ResponseEntity.ok(
                productService.getByPriceRange(min, max)
        );
    }

    // =========================
    // ADMIN ENDPOINTS
    // =========================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Productdto> createProduct(
            @RequestBody Productdto dto) {

        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Productdto> updateProduct(
            @PathVariable Long id,
            @RequestBody Productdto dto) {

        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Productdto> updatePartial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return ResponseEntity.ok(productService.updatePartial(id, updates));
    }
}
