# Discrete Microservices Architecture Implementation Plan

## 1. Create Domain Modules (Customer and Account)

### Customer Module:
1. Create Spring Boot project with dependencies: Web, JPA, H2, Lombok
2. Create Customer entity with fields: id, firstName, lastName, email
3. Create CustomerRepository interface extending JpaRepository
4. Create CustomerService class (not interface yet)
5. Create CustomerController with basic CRUD operations

### Account Module:
1. Create Spring Boot project with same dependencies
2. Create CurrencyType enum: EUR, USD
3. Create Account entity with fields: id, balance, customerId, currency
4. Create AccountRepository interface extending JpaRepository
5. Create AccountService class (not interface yet)
6. Create AccountController with basic CRUD operations

## 2. Add Feign, Swagger, and Test Data

1. Add OpenFeign dependency to Account module
2. Create CustomerClient interface in Account module:
   ```java
   @FeignClient(name = "CUSTOMER")
   public interface CustomerClient {
       @GetMapping("/customers/{id}")
       Customer getCustomer(@PathVariable Long id);
   }
   ```
3. Create fake Customer class in Account module (not an entity, just for data)
4. Add @EnableFeignClients to Account application class
5. Add Swagger UI dependency to both modules
6. Create test data with CommandLineRunner in both modules

## 3. Implement DTOs, Mappers, and Service Interfaces

1. Create DTOs in both modules
   ```java
   // CustomerDTO with same fields as entity
   // AccountDTO with same fields plus customer object
   ```
2. Create Mapper classes in both modules
3. Create Service interfaces in both modules
4. Implement ServiceImpl classes that use mappers
5. Modify controllers to use DTOs and service interfaces

## 4. Set Up Static Gateway

1. Create new Spring Boot project with Spring Cloud Gateway dependency
2. Configure static routes in application.yml:
   ```yaml
   spring:
     cloud:
       gateway:
         routes:
           - id: customer-route
             uri: http://localhost:8080
             predicates:
               - Path=/customers/**
           - id: account-route
             uri: http://localhost:8081
             predicates:
               - Path=/accounts/**
   ```
3. Set server port to 8888

## 5. Set Up Service Discovery

1. Create new Spring Boot project with Eureka Server dependency
2. Add @EnableEurekaServer to main application class
3. Configure application.yml with port 8761
4. Disable self-registration with fetch-registry: false and register-with-eureka: false
5. Add Eureka Client dependency to all services
6. Add @EnableDiscoveryClient to all application classes
7. Configure eureka.client.serviceUrl.defaultZone in all services

## 6. Implement Dynamic Gateway

1. Modify Gateway's application.yml:
   ```yaml
   spring:
     cloud:
       gateway:
         discovery:
           locator:
             enabled: true
   ```
2. Remove static routes
3. Update Feign clients to use service name instead of URL

## 7. Add Circuit Breaker

1. Add Resilience4J dependencies to Account module
2. Modify Feign client:
   ```java
   @FeignClient(name = "CUSTOMER")
   public interface CustomerClient {
       @GetMapping("/customers/{id}")
       @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomer")
       Customer getCustomer(@PathVariable Long id);
       
       default Customer getDefaultCustomer(Long id, Exception ex) {
           Customer customer = new Customer();
           customer.setId(id);
           customer.setFirstName("Not Available");
           // Set other default values
           return customer;
       }
   }
   ```
3. Add circuit breaker configuration in application.yml

## 8. Set Up Config Server

1. Create new Spring Boot project with Config Server dependency
2. Add @EnableConfigServer to main application class
3. Configure application.yml with port 9999
4. Create config-repo directory with configuration files:
   - customer.yml
   - account.yml
   - discovery.yml
   - gateway.yml
5. Move configuration from each service to its respective file
6. Add Spring Cloud Config Client to all services
7. Configure all services to use config server
8. Add Spring Boot Actuator to all services
9. Configure actuator endpoints for refresh

## 9. Dockerize Applications

1. Create Dockerfile for each service:
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY build/libs/*.jar app.jar
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```
2. Build Docker images for each service
3. Test each container individually

## 10. Create Docker Compose

Create docker-compose.yml:
```yaml
services:
  config-server:
    build: ./config
    ports:
      - "9999:9999"
    volumes:
      - ./config-repo:/config-repo
    networks:
      - app-network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:9999/actuator/health"]
      interval: 10s
      retries: 5

  discovery-server:
    build: ./discovery
    ports:
      - "8761:8761"
    depends_on:
      config-server:
        condition: service_healthy
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:9999
    networks:
      - app-network

  customer-service:
    build: ./customer
    depends_on:
      discovery-server:
        condition: service_started
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:9999
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://discovery-server:8761/eureka/
    networks:
      - app-network

  account-service:
    build: ./account
    depends_on:
      discovery-server:
        condition: service_started
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:9999
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://discovery-server:8761/eureka/
    networks:
      - app-network

  gateway-service:
    build: ./gateway
    ports:
      - "8888:8888"
    depends_on:
      discovery-server:
        condition: service_started
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:9999
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://discovery-server:8761/eureka/
    networks:
      - app-network

networks:
  app-network:
    driver: bridge
```

Following this plan will create a complete microservices architecture with configuration management, service discovery, API gateway, circuit breaking, and Docker deployment.