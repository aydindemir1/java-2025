# Java 41 — Spring Security & JWT Authentication

A focused Spring Boot learning project that demonstrates **stateless authentication and role-based authorization with Spring Security and JWT**.

The project covers the complete request flow from user registration and login to JWT generation, JWT validation, Spring Security filter-chain processing, authentication context creation, and role-protected REST endpoints.

> This project is part of the `java-backend-spring-boot-spring-cloud` learning repository and is based on the Bezkoder Spring Boot JWT authentication example, with additional local setup and security-response improvements.

---

## Features

- User registration
- User login
- JWT access-token generation
- JWT validation on incoming requests
- Stateless Spring Security configuration
- BCrypt password hashing
- Role-Based Access Control (RBAC)
- Method-level authorization with `@PreAuthorize`
- Custom JWT authentication filter
- Custom `401 Unauthorized` handling
- Custom `403 Forbidden` handling
- Spring Data JPA persistence
- Many-to-Many User–Role relationship
- Jakarta Bean Validation
- PostgreSQL database (active)
- MySQL database (alternative)
- Flyway database migrations
- Docker Compose development databases

---

## Technology Stack

| Technology | Version / Usage |
|---|---|
| Java | 17 |
| Spring Boot | 3.1.0 |
| Spring Security | Authentication & authorization |
| Spring Web | REST API |
| Spring Data JPA | Persistence |
| Hibernate | ORM |
| Jakarta Validation | Request validation |
| JJWT | 0.11.5 |
| BCrypt | Password hashing |
| PostgreSQL | Primary relational database |
| MySQL | Alternative relational database |
| Flyway | Versioned schema migrations |
| Docker Compose | Local PostgreSQL/MySQL environments |
| Maven | Build and dependency management |

---

## Core Concepts Practiced

This project is intended to reinforce the following Spring Security concepts:

- Authentication
- Authorization
- RBAC
- `SecurityFilterChain`
- `AuthenticationManager`
- `AuthenticationProvider`
- `DaoAuthenticationProvider`
- `UserDetails`
- `UserDetailsService`
- `SecurityContext`
- `OncePerRequestFilter`
- Bearer Token authentication
- JWT claims and expiration
- BCrypt password encoding
- Stateless REST APIs
- `401 Unauthorized` vs `403 Forbidden`
- JPA entity relationships
- Many-to-Many mapping

---

## Project Structure

```text
src/main/java/com/bezkoder/springjwt
├── controllers
│   ├── AuthController.java
│   └── TestController.java
├── models
│   ├── ERole.java
│   ├── Role.java
│   └── User.java
├── payload
│   ├── request
│   │   ├── LoginRequest.java
│   │   └── SignupRequest.java
│   └── response
│       ├── JwtResponse.java
│       └── MessageResponse.java
├── repository
│   ├── RoleRepository.java
│   └── UserRepository.java
├── security
│   ├── WebSecurityConfig.java
    ├── jwt
    │   ├── AuthAccessDeniedHandler.java
    │   ├── AuthEntryPointJwt.java
    │   ├── AuthTokenFilter.java
    │   └── JwtUtils.java
    └── services
│       ├── UserDetailsImpl.java
│       └── UserDetailsServiceImpl.java
└── resources
    ├── application.properties
    └── db/migration
        ├── postgresql
        │   ├── V1__create_security_schema.sql
        │   └── V2__seed_roles.sql
        └── mysql
            ├── V1__create_security_schema.sql
            └── V2__seed_roles.sql
```

---

## Authentication Flow

The JWT authentication flow is:

```text
Client
  │
  │ POST /api/auth/signin
  ▼
AuthController
  │
  ▼
AuthenticationManager
  │
  ▼
DaoAuthenticationProvider
  │
  ├── UserDetailsService
  └── PasswordEncoder (BCrypt)
  │
  ▼
Authenticated User
  │
  ▼
JwtUtils
  │
  ▼
JWT Access Token
```

For protected requests:

```text
HTTP Request
  │
  │ Authorization: Bearer <JWT>
  ▼
Spring Security Filter Chain
  │
  ▼
AuthTokenFilter
  │
  ├── Extract JWT
  ├── Validate JWT
  ├── Load UserDetails
  └── Create Authentication
  │
  ▼
SecurityContext
  │
  ▼
@PreAuthorize
  │
  ▼
Controller
```

The original architecture diagrams are also included in this project:

![JWT authentication flow](spring-boot-jwt-authentication-spring-security-flow.png)

![Spring Security architecture](spring-boot-jwt-authentication-spring-security-architecture.png)

---

## Roles

The application supports three roles:

```text
ROLE_USER
ROLE_MODERATOR
ROLE_ADMIN
```

The relationship between users and roles is modeled as **Many-to-Many**.

Role initialization is now owned by Flyway. `V2__seed_roles.sql` inserts the three base roles as part of database migration:

```text
ROLE_USER
ROLE_MODERATOR
ROLE_ADMIN
```

This removes application-startup seeding logic and keeps schema/data initialization versioned and repeatable.

---

## API Endpoints

### Authentication

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| POST | `/api/auth/signup` | Register a new user | Public |
| POST | `/api/auth/signin` | Authenticate and receive JWT | Public |

### Authorization Test Endpoints

| Method | Endpoint | Required Role |
|---|---|---|
| GET | `/api/test/all` | Public |
| GET | `/api/test/user` | USER, MODERATOR or ADMIN |
| GET | `/api/test/mod` | MODERATOR |
| GET | `/api/test/admin` | ADMIN |

---

## Running the Project

### Prerequisites

Make sure the following tools are installed:

- JDK 17+
- Docker Desktop
- Maven, or use the included Maven Wrapper
- Postman or another REST client

### 1. Start PostgreSQL

From the project directory:

```bash
docker compose up -d postgres
```

The active datasource uses PostgreSQL 17 on host port `5432`.

Check the container health:

```bash
docker compose ps
```

To run the alternative MySQL environment instead:

```bash
docker compose up -d mysql
```

Then switch the datasource block in `application.properties` from PostgreSQL to the commented MySQL configuration.

### 2. Start the Spring Boot application

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

The API runs by default at:

```text
http://localhost:8080
```

### 3. Flyway migration

No manual schema creation or role insert is required. On startup, Flyway selects migrations using the active database vendor:

```text
PostgreSQL -> classpath:db/migration/postgresql
MySQL      -> classpath:db/migration/mysql
```

Migration order:

```text
V1__create_security_schema.sql
V2__seed_roles.sql
```

Flyway also creates `flyway_schema_history` to track applied migrations. Hibernate uses `ddl-auto=validate`, so Flyway owns schema creation while Hibernate verifies entity/schema compatibility.

---

## Example Requests

### Register a USER

```http
POST /api/auth/signup
Content-Type: application/json
```

```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "StrongPassword123!",
  "role": ["user"]
}
```

Supported registration role values:

```text
user
mod
admin
```

If the role field is omitted, the application assigns `ROLE_USER`.

### Sign in

```http
POST /api/auth/signin
Content-Type: application/json
```

```json
{
  "username": "testuser",
  "password": "StrongPassword123!"
}
```

A successful login returns a JWT access token together with user information and granted roles.

### Call a protected endpoint

```http
GET /api/test/user
Authorization: Bearer <JWT_TOKEN>
```

---

## 401 vs 403

The project explicitly distinguishes authentication and authorization failures.

### 401 Unauthorized

Returned when authentication cannot be established, for example:

- Missing JWT
- Invalid JWT
- Malformed JWT
- Expired JWT

Conceptually:

```text
No valid authentication
        ↓
AuthenticationEntryPoint
        ↓
401 Unauthorized
```

### 403 Forbidden

Returned when the user is authenticated but does not have the role required by the endpoint.

Example:

```text
ROLE_USER token
        ↓
GET /api/test/admin
        ↓
Authenticated but not authorized
        ↓
AccessDeniedHandler
        ↓
403 Forbidden
```

The project contains an explicit `AccessDeniedHandler` configuration so role failures return `403 Forbidden` rather than being confused with authentication failures.

---

## Authorization Matrix

| Request | Expected Result |
|---|---|
| No token → `/api/test/user` | 401 Unauthorized |
| Invalid token → `/api/test/user` | 401 Unauthorized |
| Valid USER token → `/api/test/user` | 200 OK |
| Valid USER token → `/api/test/admin` | 403 Forbidden |
| Valid MODERATOR token → `/api/test/mod` | 200 OK |
| Valid ADMIN token → `/api/test/admin` | 200 OK |

---

## Security Configuration

The application is configured as a stateless REST API:

```java
.sessionManagement(session ->
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
)
```

CSRF is disabled for this token-based API, and JWT processing is performed before Spring Security's `UsernamePasswordAuthenticationFilter`.

Method-level authorization is enabled with:

```java
@EnableMethodSecurity
```

Role checks are applied using expressions such as:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

## Password Security

Passwords are never stored as plain text by the application.

Spring Security's `BCryptPasswordEncoder` is used:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

During login, the authentication provider compares the submitted password with the BCrypt hash stored in the database.

---

## Configuration and Secrets

The current project is a learning implementation. For production-style configuration, database credentials and JWT signing keys should **not** be committed directly to source control.

Prefer environment variables or an external secrets-management solution, for example:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

bezkoder.app.jwtSecret=${JWT_SECRET}
bezkoder.app.jwtExpirationMs=${JWT_EXPIRATION_MS:86400000}
```

For real-world systems, use a sufficiently strong signing key and manage secret rotation appropriately.

---

## Milestone Status

### ✅ Milestone 1 — Clean and Working Security Baseline

Milestone 1 refactors the original educational implementation without changing its core authentication model.

Completed improvements:

- Replaced field injection with constructor injection across security and authentication components
- Removed obsolete and commented-out Spring Security configuration
- Extracted `403 Forbidden` handling into `AuthAccessDeniedHandler`
- Kept `401 Unauthorized` handling isolated in `AuthEntryPointJwt`
- Reused Spring's managed `ObjectMapper` for security error responses
- Restricted URL-level public access to `/api/auth/**` and `/api/test/all`
- Kept role authorization at method level with `@PreAuthorize`
- Cleaned the JWT filter and JWT utility implementation
- Added `hashCode()` alongside `equals()` in `UserDetailsImpl`
- Added readable request-validation messages
- Removed password-policy validation from the persisted BCrypt hash field
- Corrected `RoleRepository` ID type from `Long` to `Integer`
- Switched repository existence queries from boxed `Boolean` to primitive `boolean`
- Added idempotent automatic role initialization
- Preserved stateless JWT authentication and the existing USER / MODERATOR / ADMIN authorization model

Expected baseline behavior:

| Scenario | Expected |
|---|---|
| Signup | 200 OK |
| Signin | 200 OK + JWT |
| Public endpoint | 200 OK |
| Protected endpoint without token | 401 Unauthorized |
| Protected endpoint with invalid token | 401 Unauthorized |
| USER → user endpoint | 200 OK |
| USER → admin endpoint | 403 Forbidden |
| MODERATOR → moderator endpoint | 200 OK |
| ADMIN → admin endpoint | 200 OK |

---

### ✅ Milestone 2 — PostgreSQL + Docker Compose + Flyway

Milestone 2 moves database lifecycle management out of Hibernate and into versioned migrations.

Completed improvements:

- Added PostgreSQL JDBC driver and kept MySQL JDBC support
- Added PostgreSQL 17 service to Docker Compose
- Retained the MySQL service for alternative local testing
- Added health checks for both database containers
- Switched the active datasource to PostgreSQL
- Preserved the MySQL datasource settings as a commented alternative
- Changed Hibernate from `ddl-auto=update` to `ddl-auto=validate`
- Added Flyway as the schema migration tool
- Added vendor-aware migration locations using `classpath:db/migration/{vendor}`
- Added PostgreSQL migrations for schema creation and role seeding
- Added equivalent MySQL migrations so the project can also be tested against MySQL
- Removed `RoleDataInitializer` because role seeding is now owned by Flyway
- Added primary/unique/foreign-key constraints explicitly in migration SQL
- Added cascading cleanup for `user_roles` foreign keys

Expected database startup flow:

```text
Docker database
    ↓
Spring Boot datasource
    ↓
Flyway
    ↓
V1 schema migration
    ↓
V2 role seed migration
    ↓
Hibernate validate
    ↓
Application ready
```

Expected PostgreSQL tables:

```text
flyway_schema_history
roles
users
user_roles
```

---

## Current Scope

Implemented in the current project:

- Spring Security authentication
- JWT access token
- Role-based authorization
- BCrypt
- UserDetails / UserDetailsService
- AuthenticationManager / AuthenticationProvider
- SecurityFilterChain
- OncePerRequestFilter
- SecurityContext
- JPA
- Many-to-Many User–Role mapping
- Validation
- 401 / 403 handling
- Stateless API
- PostgreSQL as the active datasource
- MySQL retained as an alternative datasource
- Docker Compose for PostgreSQL and MySQL
- Flyway versioned schema migrations
- Hibernate schema validation

Not yet implemented in this project:

- Refresh Token
- Logout / token revocation
- Global exception handling
- OpenAPI / Swagger
- Testcontainers
- Security integration tests
- Environment-based secret management
- Stronger JWT key-management strategy
- Audit logging
- Rate limiting

These are intended as future extensions after the core Spring Security and JWT flow is fully understood.

---

## Roadmap

- ✅ **Milestone 1** — Clean and working educational baseline
- ✅ **Milestone 2** — PostgreSQL + Docker Compose + Flyway migrations
- ⏳ **Milestone 3** — Refresh Token + logout + revocation
- ⏳ **Milestone 4** — OpenAPI + global exception handling
- ⏳ **Milestone 5** — Security unit/integration tests + Testcontainers
- ⏳ **Milestone 6** — Secrets + JWT key management + audit logging
- ⏳ **Milestone 7** — Rate limiting + production hardening

---

## Reference

This learning project is based on the following Bezkoder example:

- **Bezkoder — Spring Boot JWT Authentication with Spring Security & Spring Data JPA**
- Source repository: `bezkoder/spring-boot-spring-security-jwt-authentication`

The code has been used as a learning baseline and extended locally to practice Spring Security behavior, Docker-based database setup, and explicit `401 Unauthorized` / `403 Forbidden` handling.

---

## Learning Objective

The goal of this project is not only to make JWT authentication work, but to understand **how Spring Security processes an HTTP request internally**:

```text
Request
  → Security Filter Chain
  → JWT Filter
  → UserDetailsService
  → Authentication
  → SecurityContext
  → Authorization
  → Controller
```

This provides the foundation for building more advanced authentication and authorization architectures in later Spring Boot and Spring Cloud projects.
