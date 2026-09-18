# Java Backend Geliştirme

Bu repository, temel Java konularından başlayarak Spring Boot tabanlı backend geliştirme, veritabanı erişimi, REST API geliştirme ve test süreçlerine kadar ilerleyen uygulamalı örnek projeleri içerir.

Repository içerisinde Java, nesne yönelimli programlama, koleksiyonlar, Stream API, exception handling, concurrency, SQL/RDBMS, JDBC, JPA/Hibernate, Spring Boot, MongoDB, Docker ve test konularına yönelik çalışmalar bulunmaktadır.

## Kullanılan Teknolojiler

* Java
* Maven
* SQL
* RDBMS
* JDBC
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Spring Data MongoDB
* Spring Data Redis
* Spring Cache
* Redis
* Lettuce
* MySQL
* PostgreSQL
* H2 Database
* MongoDB
* Jakarta Validation
* Swagger / OpenAPI
* Spring Boot Actuator
* SLF4J
* Logback
* Docker
* Docker Compose
* RedisInsight
* JUnit 5
* Mockito
* MockMvc
* AssertJ
* Testcontainers
* React
* Angular
* Vue

## Repository İçeriği

### Core Java

```text
java-01  Hello World
java-02  Değişkenler
java-03  Operatörler
java-04  Döngüler
java-05  Karar ve Kontrol Mekanizmaları
java-06  Metotlar
java-07  Diziler
java-08  Object ve Class
java-09  Composition
java-10  Encapsulation
java-11  Inheritance
java-12  Polymorphism
java-13  Polymorphism
java-14  Interface
java-15  Abstract Class ve Method
java-16  Collections
java-17  Generics
java-18  Stream API
java-19  Optional
java-20  Exception Handling
java-21  Concurrency ve Multithreading
```

### Spring Boot

`java-35-spring-boot-hello`

Spring Boot, REST API, katmanlı mimari, Spring Data JPA, Hibernate, MySQL ve Docker Compose konularını içerir.

`java-36-spring-boot-exception`

REST API exception handling, Bean Validation, Spring Profiles, PostgreSQL/MySQL, Swagger ve Actuator konularını içerir.

`java-37-mongo-crud`

Spring Boot + MongoDB CRUD REST API ile React, Angular ve Vue frontend uygulamalarını içerir.

`java-38-testing-unit-webmvc-datajpa`

JUnit 5, Mockito, `@WebMvcTest`, MockMvc, `@DataJpaTest`, H2 ve AssertJ kullanılarak Spring Boot test örneklerini içerir.

`java-39-spring-boot-logging`

SLF4J ve Logback kullanılarak log seviyeleri, console logging, file logging ve exception logging örneklerini içerir.

`java-40-spring-boot-redis`

Spring Data Redis ve Spring Cache ile Redis veri yapıları, TTL, cache-aside, Pub/Sub, Streams, transaction, distributed lock, rate limiting, idempotency, Testcontainers ve Docker tabanlı Redis/RedisInsight kullanım örneklerini içerir.

## Backend Mimarisi

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate / Spring Data MongoDB
   ↓
Database
```

## Ana Konular

* Java temelleri
* Nesne yönelimli programlama
* Collections ve Generics
* Stream API
* Exception Handling
* Concurrency ve Multithreading
* SQL ve ilişkisel veritabanları
* JDBC
* REST API geliştirme
* Katmanlı mimari
* Dependency Injection
* Spring Data JPA
* Hibernate ORM
* MongoDB
* DTO ve Validation
* Global Exception Handling
* Logging (SLF4J / Logback)
* Redis ve Spring Data Redis
* Cache yönetimi ve TTL
* Redis Pub/Sub ve Streams
* Distributed Lock ve Rate Limiting
* Docker ve Docker Compose
* Unit Test
* Web MVC Test
* Repository Test
* Testcontainers
* Full Stack REST API entegrasyonu

## Amaç

Bu repository'nin amacı, Java'nın temel konularından başlayarak modern backend geliştirme süreçlerine adım adım ilerlemek ve Spring Boot ekosistemini uygulamalı örneklerle öğrenmektir.

Repository ilerleyen aşamalarda ileri seviye backend, veritabanı, test, Spring Cloud ve dağıtık sistem konuları ile genişletilecektir.
