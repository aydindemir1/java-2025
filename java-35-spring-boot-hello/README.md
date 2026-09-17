# Java 35 - Spring Boot REST API

Java 21 ve Spring Boot 4.1.1 kullanılarak geliştirilmiş basit bir **Student Management REST API** projesidir.

Proje; Spring Web MVC, Spring Data JPA, Hibernate, MySQL ve Docker Compose kullanarak temel CRUD işlemlerini göstermektedir.

## Technologies

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL 8.4
- Docker Compose
- Maven
- Lombok
- Jakarta Validation
- Spring Boot Actuator
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
MySQL
