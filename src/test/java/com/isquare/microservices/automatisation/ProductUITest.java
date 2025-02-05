package com.isquare.microservices.automatisation;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductUITest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebDriver webDriver;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    void shouldNavigateProductPage() {
        webDriver.get(baseUrl + "/products");
        WebElement title = webDriver.findElement(By.tagName("h1"));
        assertEquals("Products List", title.getText());
    }

    @Test
    @Order(2)
    void shouldCreateProduct() {
        webDriver.get(baseUrl + "/products/new");
        webDriver.findElement(By.id("name")).sendKeys("New Product");
        webDriver.findElement(By.id("description")).sendKeys("New Description");
        webDriver.findElement(By.id("price")).sendKeys("99.99");
        webDriver.findElement(By.tagName("form")).submit();

        webDriver.get(baseUrl + "/products");
        WebElement productName = webDriver.findElement(By.xpath("//td[text()='New Product']"));
        assertEquals("New Product", productName.getText());
    }

    @Test
    @Order(3)
    void shouldViewProductDetails() {
        webDriver.get(baseUrl + "/products");
        WebElement detailsLink = webDriver.findElement(By.xpath("//td[text()='New Product']/following-sibling::td/a[text()='Details']"));
        detailsLink.click();

        WebElement title = webDriver.findElement(By.tagName("h1"));
        assertEquals("Product Details", title.getText());
    }

    @Test
    @Order(4)
    void shouldDeleteProduct() {
        webDriver.get(baseUrl + "/products");
        WebElement deleteButton = webDriver.findElement(By.xpath("//td[text()='New Product']/following-sibling::td/form/button"));
        deleteButton.click();

        webDriver.get(baseUrl + "/products");
        boolean isProductDeleted = webDriver.findElements(By.xpath("//td[text()='New Product']")).isEmpty();
        assertEquals(true, isProductDeleted);
    }
}