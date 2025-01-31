package com.isquare.microservices.service;

import com.isquare.microservices.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import java.util.Map;

public interface ProductService {
    Product createProduct(@Valid Product product);
    Product getProduct(@NotNull Long id);
    List<Product> getAllProducts();
    List<Product> searchProductsByName(String name);
    Product updateProduct(Long id, Product product);
    Product partialUpdateProduct(Long id, Map<String, Object> updates);
    void deleteProduct(@NotNull Long id);
    void deleteAllProducts();
    boolean existsById(Long id);
    long countProducts();
}