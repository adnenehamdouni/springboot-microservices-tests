# Getting Started

## Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/maven-plugin/build-image.html)

## Project Setup

### Prerequisites

- Java 17
- Maven
- MySQL

### Installation Steps

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

# Help Guide

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL

### Installation Steps

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

## Profiles

- **dev**: Uses H2 in-memory database.
- **qa**: Uses MySQL database for QA environment.
- **prod**: Uses MySQL database for production environment.

## API Documentation

API documentation is available via Swagger:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- API Docs: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Postman

A Postman collection is available to test the API endpoints. Import the `postman_collection.json` file into Postman.

## Testing

### Unit and Integration Tests

Unit and integration tests are located in the `src/test/java` directory and can be run using:
```sh
mvn test
```

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

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
    - Ensure MySQL server is running.
    - Verify database URL, username, and password in `application-qa.yaml`.

2. **Port Already in Use**:
    - Change the port in `application.yaml` or stop the process using the port.

### Useful Commands

- **Start MySQL Server**:
    ```sh
    sudo service mysql start
    ```

- **Check MySQL Connection**:
    ```sh
    mysql -h localhost -P 8889 -u root -p
    ```

## Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/guides/10-minute-tutorial/)

## Contact

For further assistance, please contact the project maintainer at [adnenehamdouni@gmail.com](mailto:adnenehamdouni@example.com).