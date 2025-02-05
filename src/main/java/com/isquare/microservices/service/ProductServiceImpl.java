package com.isquare.microservices.service;

import com.isquare.microservices.config.ProductAlreadyExistsException;
import com.isquare.microservices.config.ProductNotFoundException;
import com.isquare.microservices.model.Product;
import com.isquare.microservices.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Validated
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        log.info("ProductService initialized");
    }

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @CachePut(value = "products", key = "#product.id")
    public Product createProduct(@Valid Product product) {
        log.info("Creating new product with name: {}", product.getName());
        List<Product> existingProducts = searchProductsByName(product.getName());

        Optional<Product> existingProduct = existingProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(product.getName()))
                .findFirst();

        if (existingProduct.isPresent()) {
            log.info("Product exists, updating product with id: {}", existingProduct.get().getId());
            return updateProduct(existingProduct.get().getId(), product);
        }

        Product savedProduct = productRepository.save(product);
        log.debug("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }


    @Override
    @Cacheable(value = "products")
    public Product getProduct(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        log.info("Searching products with name containing: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        log.info("Updating product with id: {}", id);
        Product existingProduct = getProduct(id);

        if (!existingProduct.getName().equals(product.getName()) &&
                productRepository.findByNameIgnoreCase(product.getName()).isPresent()) {
            log.error("Product already exists with name: {}", product.getName());
            throw new ProductAlreadyExistsException("Product with name " + product.getName() + " already exists");
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);
        log.debug("Product updated successfully: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public Product partialUpdateProduct(Long id, Map<String, Object> updates) {
        log.info("Partially updating product with id: {}", id);
        Product existingProduct = getProduct(id);

        if (updates.containsKey("name")) {
            String newName = (String) updates.get("name");
            if (!existingProduct.getName().equals(newName) &&
                    productRepository.findByNameIgnoreCase(newName).isPresent()) {
                log.error("Product already exists with name: {}", newName);
                throw new ProductAlreadyExistsException("Product with name " + newName + " already exists");
            }
            existingProduct.setName(newName);
        }

        if (updates.containsKey("description")) {
            existingProduct.setDescription((String) updates.get("description"));
        }

        if (updates.containsKey("price")) {
            existingProduct.setPrice(Double.valueOf(updates.get("price").toString()));
        }

        Product updatedProduct = productRepository.save(existingProduct);
        log.debug("Product partially updated: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(@NotNull Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            log.error("Product not found with id: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        try {
            productRepository.deleteById(id);
            log.debug("Product deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new RuntimeException("Error deleting product");
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteAllProducts() {
        log.info("Deleting all products");
        productRepository.deleteAll();
        log.debug("All products deleted successfully");
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Cacheable(value = "products-count")
    public long countProducts() {
        log.info("Counting all products");
        return productRepository.count();
    }
}
