package com.aydindemir.redis.controller; import java.time.Duration; import java.util.Map; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import com.aydindemir.redis.dto.KeyValueRequest; import com.aydindemir.redis.service.RedisStringService; import io.swagger.v3.oas.annotations.tags.Tag; import jakarta.validation.Valid;
@RestController @RequestMapping("/api/redis/strings") @Tag(name="01 - Redis String & Key Operations") public class RedisBasicsController { private final RedisStringService s; public RedisBasicsController(RedisStringService s){this.s=s;}
 @PutMapping public ResponseEntity<Void> set(@Valid @RequestBody KeyValueRequest r){s.set(r.key(),r.value(),r.ttlSeconds());return ResponseEntity.noContent().build();}
 @GetMapping("/{key}") public ResponseEntity<Map<String,String>> get(@PathVariable String key){return s.get(key).map(v->ResponseEntity.ok(Map.of("key",key,"value",v))).orElseGet(()->ResponseEntity.notFound().build());}
 @DeleteMapping("/{key}") public ResponseEntity<Void> delete(@PathVariable String key){return s.delete(key)?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
 @GetMapping("/{key}/exists") public Map<String,Object> exists(@PathVariable String key){return Map.of("key",key,"exists",s.exists(key));}
 @PutMapping("/{key}/expire/{seconds}") public Map<String,Object> expire(@PathVariable String key,@PathVariable long seconds){return Map.of("key",key,"updated",s.expire(key,Duration.ofSeconds(seconds)));}
 @GetMapping("/{key}/ttl") public Map<String,Object> ttl(@PathVariable String key){return Map.of("key",key,"ttlSeconds",s.ttl(key));}
 @PostMapping("/{key}/increment") public Map<String,Object> inc(@PathVariable String key){return Map.of("key",key,"value",s.increment(key));}
 @PostMapping("/{key}/decrement") public Map<String,Object> dec(@PathVariable String key){return Map.of("key",key,"value",s.decrement(key));}
}
