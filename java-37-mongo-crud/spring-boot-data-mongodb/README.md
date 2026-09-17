# Spring Boot MongoDB CRUD REST API

Java 17 ve Spring Boot 3.1.0 kullanılarak geliştirilmiş bir **Tutorial Management REST API** projesidir.

Uygulama; Spring Data MongoDB ile MongoDB veritabanına bağlanır ve tutorial kayıtları üzerinde CRUD işlemleri, validation, merkezi exception handling ve Swagger/OpenAPI desteği sağlar.

## Technologies

- Java 17
- Spring Boot 3.1.0
- Spring Web
- Spring Data MongoDB
- MongoDB 6.0
- Jakarta Validation
- Springdoc OpenAPI / Swagger
- Lombok
- Docker Compose
- Maven
- JUnit 5
- Mockito
- MockMvc

## Architecture

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Spring Data MongoDB
   ↓
MongoDB
