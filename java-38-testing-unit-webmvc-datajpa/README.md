# Java 38 - Spring Boot Testing

Java 17 ve Spring Boot 3.1.0 kullanılarak geliştirilmiş, **Unit Test, Web MVC Test ve Data JPA Test** yaklaşımlarını birlikte gösteren bir Spring Boot REST API projesidir.

Uygulama; katmanlı mimari, Spring Data JPA, H2 Database, validation ve merkezi exception handling yapısı üzerinde farklı test seviyelerinin uygulanmasını göstermektedir.

## Technologies

- Java 17
- Spring Boot 3.1.0
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Jakarta Validation
- JUnit 5
- Mockito
- AssertJ
- MockMvc
- Maven

## Architecture

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Spring Data JPA / Hibernate
   ↓
H2 Database
