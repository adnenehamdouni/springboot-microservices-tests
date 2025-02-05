package com.isquare.microservices.service;

import com.isquare.microservices.config.ProductNotFoundException;
import com.isquare.microservices.model.Product;
import com.isquare.microservices.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(99.99);
    }

    @Test
    void createProduct_ShouldSaveProduct() {
        // Arrange
        lenient().when(productRepository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class)))
                .thenReturn(testProduct);

        // Act
        Product result = productService.createProduct(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getName(), result.getName());
        verify(productRepository).save(any(Product.class));
    }


    @Test
    void getProduct_WhenExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Product result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
    }

    @Test
    void getProduct_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct(1L);
        });
    }

    @Test
    void getAllProducts_ShouldReturnList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        List<Product> results = productService.getAllProducts();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void deleteProduct_WhenExists_ShouldDelete() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void updateProduct_WhenExists_ShouldUpdate() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.updateProduct(1L, testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        verify(productRepository).save(any(Product.class));
    }
}


