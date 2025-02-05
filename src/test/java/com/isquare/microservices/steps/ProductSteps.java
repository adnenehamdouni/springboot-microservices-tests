package com.isquare.microservices.steps;

import com.isquare.microservices.config.ProductNotFoundException;
import com.isquare.microservices.model.Product;
import com.isquare.microservices.service.ProductService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSteps {

    @Autowired
    private ProductService productService;
    private Product product;
    private List<Product> products;
    private long productCount;

    @Given("I have a product with name {string} and price {double}")
    public void i_have_a_product_with_name_and_price(String name, double price) {
        product = new Product();
        product.setName(name);
        product.setPrice(price);
    }

    @When("I create the product")
    public void i_create_the_product() {
        product = productService.createProduct(product);
    }

    @Then("the product should be created successfully")
    public void the_product_should_be_created_successfully() {
        assertNotNull(product.getId());
    }

    @Given("a product with ID {long} exists")
    public void a_product_with_id_exists(Long id) {
        product = new Product();
        product.setId(id);
        product.setName("Existing Product");
        product.setPrice(99.99);
        productService.createProduct(product);
    }

    @When("I get the product by ID {long}")
    public void i_get_the_product_by_id(Long id) {
        product = productService.getProduct(id);
    }

    @Then("the product with ID {long} should be returned")
    public void the_product_with_id_should_be_returned(Long id) {
        assertNotNull(product);
        assertEquals(id, product.getId());
    }

    @When("I update the product with ID {long} to have name {string} and price {double}")
    public void i_update_the_product_with_id_to_have_name_and_price(Long id, String name, double price) {
        product = new Product();
        product.setName(name);
        product.setPrice(price);
        productService.updateProduct(id, product);
    }

    @Then("the product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        assertNotNull(product);
        assertEquals("Updated Product", product.getName());
        assertEquals(199.99, product.getPrice());
    }

    @When("I partially update the product with ID {long} to have name {string}")
    public void i_partially_update_the_product_with_id_to_have_name(Long id, String name) {
        product = productService.partialUpdateProduct(id, Map.of("name", name));
    }

    @Then("the product should be partially updated successfully")
    public void the_product_should_be_partially_updated_successfully() {
        assertNotNull(product);
        assertEquals("Partially Updated Product", product.getName());
    }

    @When("I delete the product with ID {long}")
    public void i_delete_the_product_with_id(Long id) {
        productService.deleteProduct(id);
    }

    @Then("the product should be deleted successfully")
    public void the_product_should_be_deleted_successfully() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(product.getId()));
    }

    @When("I get all products")
    public void i_get_all_products() {
        products = productService.getAllProducts();
    }

    @Then("the list of products should be returned")
    public void the_list_of_products_should_be_returned() {
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @When("I search products by name {string}")
    public void i_search_products_by_name(String name) {
        products = productService.searchProductsByName(name);
    }

    @Then("the list of products with name containing {string} should be returned")
    public void the_list_of_products_with_name_containing_should_be_returned(String name) {
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getName().contains(name)));
    }

    @When("I count all products")
    public void i_count_all_products() {
        productCount = productService.countProducts();
    }

    @Then("the total number of products should be returned")
    public void the_total_number_of_products_should_be_returned() {
        assertTrue(productCount > 0);
    }
}
