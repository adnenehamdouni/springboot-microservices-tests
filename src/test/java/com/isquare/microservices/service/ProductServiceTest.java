package com.isquare.microservices.service;

import com.isquare.microservices.model.Product;
import com.isquare.microservices.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProduct() {
        // Given
        Product product = new Product(null, "Test Product", "Description", 99.99);
        Product savedProduct = new Product(1L, "Test Product", "Description", 99.99);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        Product result = productService.createProduct(product);

        // Then
        assertNotNull(result);
        assertEquals(savedProduct.getId(), result.getId());
        verify(productRepository).save(any(Product.class));
    }
}

