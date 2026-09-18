# Java 41 — Spring Security ve JWT Kimlik Doğrulama

Bu proje, **Spring Security ve JWT kullanarak stateless kimlik doğrulama ve rol tabanlı yetkilendirme** konularını uygulamalı olarak öğrenmek amacıyla geliştirilmiş bir Spring Boot eğitim projesidir.

Proje; kullanıcı kaydı ve girişinden JWT üretimine, JWT doğrulamasından Spring Security filter chain akışına, `SecurityContext` oluşturulmasına ve rol korumalı REST endpoint'lerine kadar uçtan uca güvenlik akışını ele alır.

> Bu çalışma, `java-backend-spring-boot-spring-cloud` eğitim repository'sinin bir parçasıdır. Bezkoder Spring Boot JWT authentication örneği temel alınmış; güvenlik davranışı, veritabanı migration yönetimi, refresh token yaşam döngüsü, OpenAPI, hata yönetimi ve test altyapısı genişletilmiştir.

---

## Özellikler

- Kullanıcı kaydı
- Kullanıcı girişi
- JWT access token üretimi
- Kısa ömürlü access token (15 dakika)
- Uzun ömürlü opaque refresh token (7 gün)
- Refresh token rotation
- Refresh token revocation
- Kullanılmış refresh token'ın tekrar kullanımını tespit etme
- Logout ve logout-all
- Refresh token değerlerinin SHA-256 hash olarak saklanması
- Gelen JWT'lerin doğrulanması
- Stateless Spring Security yapılandırması
- BCrypt parola hashleme
- Role-Based Access Control (RBAC)
- `@PreAuthorize` ile method-level authorization
- Özel JWT authentication filter
- Özel `401 Unauthorized` yönetimi
- Özel `403 Forbidden` yönetimi
- Spring Data JPA persistence
- User–Role Many-to-Many ilişkisi
- Jakarta Bean Validation
- PostgreSQL ana veritabanı desteği
- MySQL alternatif veritabanı desteği
- Flyway ile versiyonlu veritabanı migration'ları
- OpenAPI / Swagger UI dokümantasyonu
- Global exception handling
- Standart API hata response modeli
- Authentication, refresh token ve JWT unit testleri
- Spring Security integration testleri
- PostgreSQL Testcontainers integration testleri
- Flyway migration'larının temiz PostgreSQL üzerinde doğrulanması
- Docker Compose ile geliştirme veritabanları

---

## Teknoloji Yığını

| Teknoloji | Kullanım |
|---|---|
| Java | 17 |
| Spring Boot | 3.1.0 |
| Spring Security | Authentication ve authorization |
| Spring Web | REST API |
| Spring Data JPA | Veri erişimi |
| Hibernate | ORM |
| Jakarta Validation | Request validation |
| JJWT | 0.11.5 |
| BCrypt | Parola hashleme |
| PostgreSQL | Ana ilişkisel veritabanı |
| MySQL | Alternatif ilişkisel veritabanı |
| Flyway | Versiyonlu veritabanı migration yönetimi |
| springdoc-openapi | OpenAPI 3 + Swagger UI |
| JUnit 5 / Mockito | Unit test |
| MockMvc / Spring Security Test | HTTP ve security integration test |
| Testcontainers | İzole PostgreSQL integration test veritabanı |
| Docker Compose | Lokal PostgreSQL/MySQL ortamı |
| Maven | Build ve dependency yönetimi |

---

## Öğrenilen Temel Konular

Bu proje özellikle aşağıdaki Spring Security kavramlarını pekiştirmeyi amaçlar:

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
- Access token / refresh token yaşam döngüsü
- Refresh token rotation ve revocation
- Refresh token reuse detection
- JWT claim ve expiration yönetimi
- BCrypt password encoding
- Stateless REST API
- `401 Unauthorized` ve `403 Forbidden` farkı
- JPA entity ilişkileri
- Many-to-Many mapping

---

## Proje Yapısı

```text
src/main/java/com/bezkoder/springjwt
├── config
│   └── OpenApiConfig.java
├── controllers
│   ├── AuthController.java
│   └── TestController.java
├── exception
│   ├── ApiErrorResponse.java
│   ├── EmailAlreadyExistsException.java
│   ├── GlobalExceptionHandler.java
│   ├── RoleNotFoundException.java
│   └── UsernameAlreadyExistsException.java
├── models
│   ├── ERole.java
│   ├── RefreshToken.java
│   ├── Role.java
│   └── User.java
├── payload
│   ├── request
│   │   ├── LoginRequest.java
│   │   ├── LogoutRequest.java
│   │   ├── RefreshTokenRequest.java
│   │   └── SignupRequest.java
│   └── response
│       ├── MessageResponse.java
│       └── TokenResponse.java
├── repository
│   ├── RefreshTokenRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
├── security
│   ├── WebSecurityConfig.java
│   ├── jwt
│   │   ├── AuthAccessDeniedHandler.java
│   │   ├── AuthEntryPointJwt.java
│   │   ├── AuthTokenFilter.java
│   │   └── JwtUtils.java
│   └── services
│       ├── AuthService.java
│       ├── RefreshTokenException.java
│       ├── RefreshTokenService.java
│       ├── UserDetailsImpl.java
│       └── UserDetailsServiceImpl.java
└── resources
    ├── application.properties
    └── db/migration
        ├── postgresql
        │   ├── V1__create_security_schema.sql
        │   ├── V2__seed_roles.sql
        │   └── V3__create_refresh_tokens.sql
        └── mysql
            ├── V1__create_security_schema.sql
            ├── V2__seed_roles.sql
            └── V3__create_refresh_tokens.sql
```

---

## Authentication Akışı

Kullanıcı girişinde temel akış:

```text
Client
  │
  │ POST /api/auth/signin
  ▼
AuthController
  │
  ▼
AuthService
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
RefreshTokenService + JwtUtils
  │
  ├── JWT Access Token
  └── Refresh Token
```

Korunan endpoint'lere gelen isteklerde:

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
  ├── JWT'yi çıkar
  ├── JWT'yi doğrula
  ├── UserDetails yükle
  └── Authentication oluştur
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

Projede orijinal mimari görselleri de bulunmaktadır:

![JWT authentication flow](spring-boot-jwt-authentication-spring-security-flow.png)

![Spring Security architecture](spring-boot-jwt-authentication-spring-security-architecture.png)

---

## Roller

Uygulama üç temel rolü destekler:

```text
ROLE_USER
ROLE_MODERATOR
ROLE_ADMIN
```

User ve Role ilişkisi **Many-to-Many** olarak modellenmiştir.

Rollerin başlangıç verileri Flyway tarafından yönetilir. `V2__seed_roles.sql` migration'ı aşağıdaki rolleri ekler:

```text
ROLE_USER
ROLE_MODERATOR
ROLE_ADMIN
```

Böylece rol seed işlemi uygulama başlangıç kodundan ayrılmış ve versiyonlu migration yapısına taşınmıştır.

---

## API Endpoint'leri

### Authentication

| Metot | Endpoint | Açıklama | Yetkilendirme |
|---|---|---|---|
| POST | `/api/auth/signup` | Yeni kullanıcı kaydı | Public |
| POST | `/api/auth/signin` | Giriş yapar ve access + refresh token döner | Public |
| POST | `/api/auth/refresh` | Refresh token rotation yapar ve yeni token çifti üretir | Public |
| POST | `/api/auth/logout` | Gönderilen refresh token'ı revoke eder | Public |
| POST | `/api/auth/logout-all` | Kullanıcının tüm aktif refresh token'larını revoke eder | Authenticated |

### Authorization Test Endpoint'leri

| Metot | Endpoint | Gerekli Rol |
|---|---|---|
| GET | `/api/test/all` | Public |
| GET | `/api/test/user` | USER, MODERATOR veya ADMIN |
| GET | `/api/test/mod` | MODERATOR |
| GET | `/api/test/admin` | ADMIN |

---

## Projeyi Çalıştırma

### Gereksinimler

- JDK 17+
- Docker Desktop
- Maven veya projedeki Maven Wrapper
- Postman veya başka bir REST istemcisi

### 1. PostgreSQL'i Başlatma

Proje dizininde:

```bash
docker compose up -d postgres
```

Aktif datasource PostgreSQL 17 kullanır ve host üzerinde `5432` portundan erişilir.

Container durumunu kontrol etmek için:

```bash
docker compose ps
```

Alternatif olarak MySQL'i çalıştırmak için:

```bash
docker compose up -d mysql
```

Ardından `application.properties` içindeki PostgreSQL bloğu yorum satırına alınarak mevcut MySQL bloğu aktif hale getirilebilir.

### 2. Spring Boot Uygulamasını Başlatma

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

API varsayılan olarak:

```text
http://localhost:8080
```

adresinde çalışır.

### 3. Flyway Migration

Manuel tablo oluşturma veya rol ekleme işlemi gerekmez. Flyway aktif veritabanı üreticisine göre uygun migration dizinini seçer:

```text
PostgreSQL -> classpath:db/migration/postgresql
MySQL      -> classpath:db/migration/mysql
```

Migration sırası:

```text
V1__create_security_schema.sql
V2__seed_roles.sql
V3__create_refresh_tokens.sql
```

Flyway ayrıca uygulanan migration'ları takip etmek için `flyway_schema_history` tablosunu oluşturur.

Hibernate tarafında:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

kullanıldığı için şemayı Flyway yönetir, Hibernate ise entity ve veritabanı şemasının uyumunu doğrular.

---

## Örnek İstekler

### USER Kaydı

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

Desteklenen kayıt rol değerleri:

```text
user
mod
admin
```

Rol alanı gönderilmezse varsayılan olarak `ROLE_USER` atanır.

### Giriş

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

Başarılı giriş sonucunda kısa ömürlü JWT access token, uzun ömürlü opaque refresh token, kullanıcı bilgileri, roller ve access token yaşam süresi döner.

Örnek response:

```json
{
  "accessToken": "eyJ...",
  "refreshToken": "opaque-random-token",
  "tokenType": "Bearer",
  "expiresIn": 900,
  "id": 1,
  "username": "testuser",
  "email": "testuser@example.com",
  "roles": ["ROLE_USER"]
}
```

### Korunan Endpoint Çağrısı

```http
GET /api/test/user
Authorization: Bearer <JWT_TOKEN>
```

---

## Refresh Token Yaşam Döngüsü

Projede iki farklı token tipi kullanılır:

| Token | Tür | Yaşam Süresi | Sunucuda Saklanma |
|---|---|---:|---|
| Access Token | İmzalı JWT | 15 dakika | Hayır |
| Refresh Token | 256-bit opaque random token | 7 gün | Yalnızca SHA-256 hash |

Raw refresh token istemciye döndürülür; veritabanında ise yalnızca SHA-256 hash değeri tutulur.

### Refresh

```http
POST /api/auth/refresh
Content-Type: application/json
```

```json
{
  "refreshToken": "<current-refresh-token>"
}
```

Başarılı refresh işleminde **rotation** uygulanır:

```text
Refresh Token A
      ↓
Hash / expiration / revocation kontrolü
      ↓
Refresh Token B oluştur
      ↓
A'yı revoke et
      ↓
A.replacedByTokenId = B.id
      ↓
Yeni Access Token + B döndür
```

Eski refresh token artık güncel session token olarak kullanılamaz.

### Refresh Token Tekrar Kullanımını Tespit Etme

Rotation uygulanmış eski bir refresh token tekrar gönderilirse sistem bunu olası token çalınması sinyali olarak değerlendirir:

```text
Daha önce rotation uygulanmış token tekrar kullanılır
          ↓
replacedByTokenId dolu
          ↓
Kullanıcının tüm aktif refresh token'larını revoke et
          ↓
İsteği reddet
```

### Logout

```http
POST /api/auth/logout
Content-Type: application/json
```

```json
{
  "refreshToken": "<current-refresh-token>"
}
```

Logout idempotent çalışır ve `204 No Content` döner. Token varsa ve aktifse revoke edilir.

### Logout All

```http
POST /api/auth/logout-all
Authorization: Bearer <access-token>
```

Bu endpoint authenticated kullanıcıya ait tüm aktif refresh token'ları revoke eder.

Access token'lar stateless tutulduğu ve blacklist kullanılmadığı için daha önce üretilmiş access token kendi kısa yaşam süresi dolana kadar geçerli kalır.

---

## 401 ve 403 Farkı

### 401 Unauthorized

Authentication oluşturulamadığında döner. Örnekler:

- JWT gönderilmemesi
- Geçersiz JWT
- Bozuk JWT
- Süresi dolmuş JWT

```text
Geçerli authentication yok
        ↓
AuthenticationEntryPoint
        ↓
401 Unauthorized
```

### 403 Forbidden

Kullanıcı authenticated olduğu halde endpoint için gerekli role sahip değilse döner.

Örnek:

```text
ROLE_USER token
        ↓
GET /api/test/admin
        ↓
Authenticated fakat yetkili değil
        ↓
AccessDeniedHandler
        ↓
403 Forbidden
```

---

## Yetkilendirme Matrisi

| İstek | Beklenen Sonuç |
|---|---|
| Token yok → `/api/test/user` | 401 Unauthorized |
| Geçersiz token → `/api/test/user` | 401 Unauthorized |
| Geçerli USER token → `/api/test/user` | 200 OK |
| Geçerli USER token → `/api/test/admin` | 403 Forbidden |
| Geçerli MODERATOR token → `/api/test/mod` | 200 OK |
| Geçerli ADMIN token → `/api/test/admin` | 200 OK |

---

## Spring Security Yapılandırması

Uygulama stateless REST API olarak yapılandırılmıştır:

```java
.sessionManagement(session ->
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
)
```

Token tabanlı API olduğu için CSRF devre dışıdır. JWT işleme, Spring Security'nin `UsernamePasswordAuthenticationFilter` filtresinden önce yapılır.

Method-level authorization:

```java
@EnableMethodSecurity
```

ile aktiftir.

Rol kontrolleri örneğin:

```java
@PreAuthorize("hasRole('ADMIN')")
```

şeklinde uygulanır.

---

## OpenAPI / Swagger UI

Interaktif API dokümantasyonu:

```text
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

adreslerinden erişilebilir.

Swagger UI içerisinde JWT Bearer Security Scheme tanımlıdır. Korunan endpoint'leri çağırmak için **Authorize** butonuna access token girilir. Swagger isteğe otomatik olarak:

```http
Authorization: Bearer <access-token>
```

header'ını ekler.

---

## Standart Hata Response Modeli

Controller ve Spring Security kaynaklı hatalar ortak bir response sözleşmesi kullanır.

Örnek validation hatası:

```json
{
  "timestamp": "2026-09-19T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "code": "VALIDATION_ERROR",
  "message": "Validation failed.",
  "path": "/api/auth/signup",
  "fieldErrors": {
    "email": "Email must be valid."
  }
}
```

Örnek hata kodları:

```text
VALIDATION_ERROR
MALFORMED_REQUEST
INVALID_CREDENTIALS
USERNAME_ALREADY_EXISTS
EMAIL_ALREADY_EXISTS
ROLE_NOT_FOUND
REFRESH_TOKEN_INVALID
UNAUTHORIZED
ACCESS_DENIED
INTERNAL_SERVER_ERROR
```

Controller exception'ları `GlobalExceptionHandler` tarafından yönetilir. Security filter seviyesindeki authentication ve authorization hataları ise `AuthEntryPointJwt` ve `AuthAccessDeniedHandler` üzerinden aynı `ApiErrorResponse` formatında döner.

---

## Testler

Proje katmanlı bir test yapısı içerir.

### Unit Testler

Spring context veya gerçek veritabanı açmadan çalışan hızlı testler:

```text
AuthServiceTest
RefreshTokenServiceTest
JwtUtilsTest
```

Bu testler duplicate user kontrolü, varsayılan rol atama, refresh token hata/reuse senaryoları ve JWT üretme/doğrulama davranışlarını kapsar.

### Security ve API Integration Testleri

Integration testleri gerçek Spring Boot application context'i ve `MockMvc` kullanır. Böylece gerçek `SecurityFilterChain` üzerinden HTTP request'leri test edilir.

```text
AuthenticationIntegrationTest
AuthorizationIntegrationTest
RefreshTokenIntegrationTest
OpenApiIntegrationTest
```

Örnek senaryolar:

```text
public endpoint, token yok           -> 200
protected endpoint, token yok        -> 401
geçersiz JWT                         -> 401
USER -> /api/test/user               -> 200
USER -> /api/test/admin              -> 403
ADMIN -> /api/test/admin             -> 200
duplicate signup                     -> 409
geçersiz signup request              -> 400 + fieldErrors
signin                               -> access + refresh token
refresh rotation                     -> yeni token çifti
eski token tekrar kullanımı          -> 401 + session revocation
logout                               -> 204
logout-all                           -> tüm refresh session'ları revoke
OpenAPI JSON                         -> 200
```

### PostgreSQL Testcontainers

Integration testleri otomatik olarak geçici bir `postgres:17` container başlatır.

Test datasource dinamik olarak container bilgileriyle yapılandırıldığı için testler lokal PostgreSQL veya MySQL kurulumuna bağlı değildir.

Test başlangıç akışı:

```text
PostgreSQL Testcontainer
        ↓
Flyway V1 / V2 / V3
        ↓
Hibernate ddl-auto=validate
        ↓
Spring Security Application Context
        ↓
MockMvc Integration Tests
```

PostgreSQL container test JVM'i boyunca açık tutulur; böylece Spring context cache farklı integration test sınıfları arasında aynı veritabanı bağlantısını kullanabilir.

### Tüm Testleri Çalıştırma

Integration testleri Testcontainers kullandığı için Docker Desktop açık olmalıdır.

Windows:

```bash
mvnw.cmd test
```

Linux/macOS:

```bash
./mvnw test
```

Test profile yapılandırması:

```text
src/test/resources/application-test.properties
```

---

## Parola Güvenliği

Parolalar uygulama tarafından düz metin olarak saklanmaz.

Spring Security `BCryptPasswordEncoder` kullanır:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Login sırasında gönderilen parola, veritabanındaki BCrypt hash ile authentication provider tarafından karşılaştırılır.

---

## Yapılandırma ve Secret Bilgileri

Bu proje eğitim amaçlıdır. Gerçek production sistemlerde veritabanı credential'ları ve JWT signing key gibi hassas bilgiler source control'e doğrudan commit edilmemelidir.

Örneğin environment variable kullanımı tercih edilebilir:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

bezkoder.app.jwtSecret=${JWT_SECRET}
bezkoder.app.jwtExpirationMs=${JWT_EXPIRATION_MS:900000}
```

---

## Milestone Durumu

### ✅ Milestone 1 — Temiz ve Çalışan Security Baseline

- Field injection yerine constructor injection kullanıldı.
- Eski ve yorum satırına alınmış Spring Security konfigürasyonu temizlendi.
- `401` ve `403` yönetimi ayrıştırıldı.
- Public URL kuralları daraltıldı.
- JWT filter ve utility kodu temizlendi.
- Request validation mesajları iyileştirildi.
- Repository tip ve dönüş değeri hataları düzeltildi.
- USER / MODERATOR / ADMIN yetkilendirme modeli korundu.

### ✅ Milestone 2 — PostgreSQL + Docker Compose + Flyway

- PostgreSQL JDBC driver eklendi.
- MySQL alternatif datasource olarak korundu.
- PostgreSQL 17 Docker Compose servisi eklendi.
- Flyway veritabanı migration aracı olarak eklendi.
- `ddl-auto=update` yerine `ddl-auto=validate` kullanıldı.
- PostgreSQL ve MySQL için vendor-specific migration yapısı oluşturuldu.
- Şema ve rol seed işlemleri Flyway'e taşındı.

### ✅ Milestone 3 — Refresh Token + Logout + Revocation

- Access token yaşam süresi 15 dakikaya indirildi.
- 7 günlük opaque refresh token eklendi.
- Refresh token değerleri SHA-256 hash olarak saklanıyor.
- Refresh token rotation eklendi.
- Pessimistic locking eklendi.
- Reuse detection eklendi.
- Logout ve logout-all eklendi.
- Access token blacklist kullanılmadan stateless JWT yapısı korundu.

### ✅ Milestone 4 — OpenAPI + Global Exception Handling

- springdoc-openapi eklendi.
- JWT Bearer authentication Swagger'a eklendi.
- Ortak `ApiErrorResponse` modeli oluşturuldu.
- `GlobalExceptionHandler` eklendi.
- Validation, malformed request, duplicate user/email ve credential hataları standardize edildi.
- `AuthService` ile controller inceltildi.
- Security katmanındaki `401` ve `403` response'ları aynı hata formatına geçirildi.

### ✅ Milestone 5 — Security Testleri + Testcontainers

- `AuthService`, `RefreshTokenService` ve `JwtUtils` unit testleri eklendi.
- Authentication integration testleri eklendi.
- Authorization ve `401/403` integration testleri eklendi.
- Refresh token rotation, reuse, logout ve logout-all integration testleri eklendi.
- OpenAPI smoke testleri eklendi.
- PostgreSQL Testcontainers altyapısı eklendi.
- Flyway migration'ları temiz PostgreSQL üzerinde test kapsamına alındı.

---

## Projenin Güncel Kapsamı

Bu eğitim projesinde aşağıdaki konular uygulanmıştır:

- Spring Security authentication
- JWT access token
- Opaque refresh token
- Refresh token rotation ve revocation
- Refresh token reuse detection
- Logout ve logout-all
- Role-Based Access Control
- BCrypt
- `UserDetails` / `UserDetailsService`
- `AuthenticationManager` / `AuthenticationProvider`
- `SecurityFilterChain`
- `OncePerRequestFilter`
- `SecurityContext`
- Spring Data JPA
- Hibernate
- Many-to-Many User–Role mapping
- Bean Validation
- Global exception handling
- Standart API error response
- PostgreSQL ve MySQL
- Docker Compose
- Flyway
- OpenAPI / Swagger UI
- JUnit 5 / Mockito
- MockMvc
- Spring Security integration testleri
- PostgreSQL Testcontainers

---

## Yol Haritası

- ✅ **Milestone 1** — Security baseline
- ✅ **Milestone 2** — PostgreSQL + Docker Compose + Flyway
- ✅ **Milestone 3** — Refresh Token + logout + revocation
- ✅ **Milestone 4** — OpenAPI + global exception handling
- ✅ **Milestone 5** — Security unit/integration testleri + Testcontainers

---

## Referans

Bu eğitim projesi aşağıdaki Bezkoder örneğini temel alır:

- **Bezkoder — Spring Boot JWT Authentication with Spring Security & Spring Data JPA**
- Kaynak repository: `bezkoder/spring-boot-spring-security-jwt-authentication`

Örnek proje, Spring Security davranışını daha ayrıntılı öğrenmek amacıyla Docker tabanlı veritabanı kurulumu, refresh token yönetimi, Flyway, OpenAPI, standart hata response'ları ve otomatik testlerle genişletilmiştir.

---

## Öğrenme Hedefi

Bu projenin amacı yalnızca JWT authentication'ı çalıştırmak değil, **Spring Security'nin bir HTTP isteğini içeride nasıl işlediğini anlamaktır**:

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

Bu yapı, ileride daha gelişmiş Spring Boot ve Spring Cloud güvenlik mimarilerini öğrenmek için temel oluşturur.
