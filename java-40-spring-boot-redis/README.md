# Java 40 - Spring Boot Redis Professional Learning Lab

Bu proje Redis'i sadece `SET/GET` seviyesinde değil; temel, orta, ileri ve production odaklı kullanım senaryolarıyla öğrenmek için hazırlanmıştır.

## Teknolojiler
Java 21, Spring Boot 4.1.1, Spring Web MVC, Spring Data Redis, Spring Cache, Redis, Lettuce, Lettuce Pool, Docker Compose, RedisInsight, Swagger/OpenAPI, Bean Validation, Actuator, JUnit 5 ve Testcontainers.

## Mimari
```text
java-40-spring-boot-redis
├── controller
├── service
├── repository
├── model
├── dto
├── config
├── exception
└── redis
    ├── cache
    ├── pubsub
    ├── stream
    ├── lock
    └── ratelimit
```

## Kapsam
- StringRedisTemplate ve RedisTemplate<String,Object>
- SET / GET / DELETE / EXISTS / TTL / EXPIRE / INCR / DECR
- String, Hash, List, Set, Sorted Set
- @EnableCaching, @Cacheable, @CachePut, @CacheEvict, cache-aside, TTL
- String + JSON serialization, key/value serializer ayrımı
- Product cache, temporary session, counter, leaderboard, recent items, unique users
- Pub/Sub, Redis Streams, MULTI/EXEC, pipelining, WATCH optimistic locking
- Distributed lock, rate limiting, idempotency key
- Key naming, cache invalidation, stampede, hot-key ölçümü, eviction policy
- SCAN, Lettuce pooling, error handling, graceful fallback
- Unit test, integration test, Testcontainers, TTL ve cache hit/miss testleri

## Docker
Docker Desktop açıkken sadece Redis + RedisInsight:
```bash
docker compose up -d redis redisinsight
```
Redis: `localhost:6379`  
RedisInsight: `http://localhost:5540`

RedisInsight bağlantısı: host `redis`, port `6379`.

STS üzerinden uygulamayı `Run As > Spring Boot App` ile çalıştır.  
Swagger: `http://localhost:8091/swagger-ui.html`  
Actuator: `http://localhost:8091/actuator/health`

Her şeyi Docker'da çalıştırmak için:
```bash
docker compose --profile full up -d --build
```

## Önerilen öğrenme sırası
1. `/api/redis/strings`
2. `/api/redis/data-structures`
3. `/api/products`
4. `/api/use-cases`
5. `/api/messaging`
6. `/api/advanced`
