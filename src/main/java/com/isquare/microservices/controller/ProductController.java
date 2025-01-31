package com.isquare.microservices.controller;

import com.isquare.microservices.model.Product;
import com.isquare.microservices.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info("Creating new product request received");
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        log.info("Searching products with name: {}", name);
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        log.info("Updating product with id: {}", id);
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a product")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        log.info("Partially updating product with id: {}", id);
        return ResponseEntity.ok(productService.partialUpdateProduct(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product with id: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete all products")
    @ApiResponse(responseCode = "204", description = "All products deleted successfully")
    public ResponseEntity<Void> deleteAllProducts() {
        log.info("Deleting all products");
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get total number of products")
    public ResponseEntity<Long> countProducts() {
        log.info("Counting all products");
        return ResponseEntity.ok(productService.countProducts());
    }
}
