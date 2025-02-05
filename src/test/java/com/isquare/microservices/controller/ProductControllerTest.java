package com.isquare.microservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isquare.microservices.config.SharedWebTestConfig;
import com.isquare.microservices.model.Product;
import com.isquare.microservices.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(SharedWebTestConfig.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createProduct_ShouldReturnCreated() throws Exception {
        when(productService.createProduct(any(Product.class)))
                .thenReturn(testProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(99.99));
    }

    @Test
    void getProduct_ShouldReturnProduct() throws Exception {
        when(productService.getProduct(1L)).thenReturn(testProduct);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void getAllProducts_ShouldReturnList() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(Product.class)))
                .thenReturn(testProduct);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchProducts_ShouldReturnFilteredList() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        when(productService.searchProductsByName("Test")).thenReturn(products);

        mockMvc.perform(get("/api/v1/products/search")
                        .param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void countProducts_ShouldReturnTotal() throws Exception {
        when(productService.countProducts()).thenReturn(1L);

        mockMvc.perform(get("/api/v1/products/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}

