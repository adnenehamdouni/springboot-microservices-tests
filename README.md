# Spring Boot Microservices Tests

## Description

This project is a Spring Boot application designed to manage products. It includes a RESTful API for CRUD operations and a web interface for managing products. The project also includes automated tests using Selenium and Cucumber.

## Technologies Used

- **Java**: 17
- **Spring Boot**: 3.4.2
- **Maven**: Dependency management and build tool
- **MySQL**: Database
- **H2**: In-memory database for development
- **Thymeleaf**: Template engine for web views
- **Spring Data JPA**: Data access layer
- **Springdoc OpenAPI**: API documentation
- **Selenium**: UI testing
- **Cucumber**: BDD testing

## Project Structure

- `src/main/java`: Contains the main application code
- `src/main/resources`: Contains configuration files and templates
- `src/test/java`: Contains test classes
- `src/test/resources`: Contains test resources

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/adnenehamdouni/springboot-microservices-tests.git
    cd springboot-microservices-tests
    ```

2. **Set up MySQL database**:
    ```sh
    mysql -u root -p
    CREATE DATABASE microservices_db;
    ```

3. **Configure application properties**:
   Update the `application-qa.yaml` file with your MySQL credentials.

### Running the Application

1. **Build the project**:
    ```sh
    mvn clean install
    ```

2. **Run the application**:
    ```sh
    mvn spring-boot:run -Dspring-boot.run.profiles=qa
    ```

3. **Access the application**:
    - API: `http://localhost:8080/api/v1/products`
    - Web UI: `http://localhost:8080/products`
    - H2 Console (dev profile): `http://localhost:8080/h2-console`

### Running Tests

1. **Unit and Integration Tests**:
    ```sh
    mvn test
    ```

2. **Selenium Tests**:
    ```sh
    mvn test -Dtest=ProductUITest
    ```

3. **Cucumber Tests**:
    ```sh
    mvn test -Dtest=CucumberTest
    ```

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License.