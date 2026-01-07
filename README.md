# Product Service

A production-ready Spring Boot 3 microservice for managing products, designed with clean architecture principles and modern Java best practices.

The service demonstrates a real-world microservice approach including caching, DTO mapping, transaction management, and comprehensive exception handling.

---

## 🚀 Tech Stack
- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **H2 / MySQL**
- **Caffeine Cache**
- **MapStruct**
- **Lombok**
- **Jakarta Validation**
- **JUnit 5 & Mockito**
- **OpenAPI / Swagger**

---
## 🏗️ Architecture

The application follows a layered architecture with a clear separation of concerns:

- **Controller Layer**
   - Exposes RESTful HTTP APIs
   - Handles request validation and response mapping

- **Service Layer**
   - Contains business logic
   - Defines transaction boundaries
   - Applies caching and domain rules

- **Repository Layer**
   - Manages persistence using Spring Data JPA

- **DTO & Mapping Layer**
   - Uses MapStruct for compile-time safe and performant mapping
   - Prevents entity leakage across layers

This design aligns with clean code, maintainability, and production-ready microservice standards.

---

## 📋 Requirements
- Java 21
- Maven

---

## ⚙️ Configuration
The application uses **H2 In-Memory Database** by default for local development.  
No additional setup is required.

### Switching to MySQL

Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product_db
    username: your_username
    password: your_password
```

---

## 🏃 Running the Application
1. Build the project:
   ```bash
   mvn clean install
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## 📖 documentation
Once the application is running, you can access the Swagger UI to test the API:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🛠️ API Endpoints

### Create Product
`POST /api/products`

**Request Body:**
```json
{
  "name": "Laptop",
  "description": "High-end gaming laptop",
  "price": 1500.00,
  "stock": 10
}
```

### Get Product by ID
`GET /api/products/{id}`

**Response:**
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-end gaming laptop",
  "price": 1500.00,
  "stock": 10
}
```

### Update Stock
`PATCH /api/products/{id}/stock`

**Request Body:**
```json
{
  "stock": 25
}
```

### Update Price
`PATCH /api/products/{id}/price`

**Request Body:**
```json
{
  "price": 1299.99
}
```

### List Products (Paginated)
`GET /api/products?page=0&size=20&sort=name,asc`

**Response:**
```json
{
  "content": [ ... ],
  "pageable": { ... },
  "totalPages": 1,
  "totalElements": 1
}
```

---

## 💾 Caching

- **Cache Provider**: Caffeine
- **Cache Name**: `productCache`
- **Expiry**: 10 minutes
- **Max Size**: 100 entries

Caching is applied at the service layer to reduce database load and improve read performance for frequently accessed products.

---

## 🔐 Transaction Management

- Read operations are executed within **read-only transactions** for performance optimization.
- Write operations explicitly define transactional boundaries at the service layer.
- Transaction management is handled according to Spring best practices.

---

## 🧪 Testing & Standards
### Test Naming Convention
Unit tests follow the `should...` naming convention for improved readability:
- Example: `shouldReturnProductWhenGetByIdExists()`

### Running Tests
Run unit tests with:
```bash
mvn test
```
