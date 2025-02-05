package com.isquare.microservices.config;

import com.isquare.microservices.service.ProductService;
import com.isquare.microservices.service.ProductServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ /* some config shared by tests */ })
public class SharedWebTestConfig {
    @Bean
    public ProductService productService() {
        return Mockito.mock(ProductServiceImpl.class);
    }
    // ...
}
