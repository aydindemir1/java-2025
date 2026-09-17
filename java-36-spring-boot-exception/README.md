# Java 36 - Spring Boot Exception Handling

Java 21 ve Spring Boot 4.1.1 kullanılarak geliştirilmiş, REST API exception handling ve validation odaklı bir Spring Boot projesidir.

Proje; Controller, Service, Repository ve Entity katmanlarının yanında merkezi hata yönetimi, validation, profile-based configuration, Swagger/OpenAPI, Actuator ve PostgreSQL/MySQL desteğini göstermektedir.

## Technologies

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL 17
- MySQL
- Jakarta Validation
- Springdoc OpenAPI / Swagger
- Spring Boot Actuator
- Lombok
- Docker Compose
- Maven
- JUnit 5

## Architecture

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate
   ↓
Database
