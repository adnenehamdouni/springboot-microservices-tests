Feature: Product Management

  Scenario: Create a new product
    Given I have a product with name "Test Product" and price 99.99
    When I create the product
    Then the product should be created successfully

  Scenario: Get a product by ID
    Given a product with ID 1 exists
    When I get the product by ID 1
    Then the product with ID 1 should be returned

  Scenario: Update a product
    Given a product with ID 1 exists
    When I update the product with ID 1 to have name "Updated Product" and price 199.99
    Then the product should be updated successfully

  Scenario: Partially update a product
    Given a product with ID 1 exists
    When I partially update the product with ID 1 to have name "Partially Updated Product"
    Then the product should be partially updated successfully

  Scenario: Get all products
    When I get all products
    Then the list of products should be returned

  Scenario: Search products by name
    Given I have a product with name "Partially Updated Product" and price 49.99
    When I search products by name "Partially Updated Product"
    Then the list of products with name containing "Partially Updated Product" should be returned

  Scenario: Count products
    When I count all products
    Then the total number of products should be returned

  Scenario: Delete a product
    Given a product with ID 1 exists
    When I delete the product with ID 1
    Then the product should be deleted successfully