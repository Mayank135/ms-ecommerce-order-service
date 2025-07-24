# Order Service - Microservices Architecture

This is the Order Service of the `microservices-ecommerce` project.
4 of the following microservices forms a part of ecommerce project based on microdervices archietecture:
- product-service: https://github.com/Mayank135/ms-ecommerce-product-service
- order-service: https://github.com/Mayank135/ms-ecommerce-order-service
- inventory-service: https://github.com/Mayank135/ms-ecommerce-inventory-service
- api-gateway:

### Tech Stack:
- Java 17
- Spring Boot
- Docker
- Testcontainers
- Maven

### Features:
- Create/Submit order
- Communicates with Inventory Service (https://github.com/Mayank135/ms-ecommerce-inventory-service) using RestClient.
- Can be run standalone or with Docker Compose

### Run Locally:

```bash
./mvnw spring-boot:run
