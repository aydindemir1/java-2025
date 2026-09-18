package com.aydindemir.redis.service;
import java.time.Instant; import java.util.Optional; import org.springframework.data.redis.core.RedisTemplate; import org.springframework.stereotype.Service;
import com.aydindemir.redis.config.RedisLearningProperties; import com.aydindemir.redis.model.TemporarySession; import com.aydindemir.redis.redis.cache.KeyNamingService;
@Service public class TemporarySessionService { private final RedisTemplate<String,Object> redis; private final RedisLearningProperties p; private final KeyNamingService keys;
 public TemporarySessionService(RedisTemplate<String,Object> redis,RedisLearningProperties p,KeyNamingService keys){this.redis=redis;this.p=p;this.keys=keys;}
 public TemporarySession create(String sid,String uid){TemporarySession s=new TemporarySession(sid,uid,Instant.now());redis.opsForValue().set(keys.key("session","temporary",sid),s,p.getSessionTtl());return s;}
 public Optional<Object> get(String sid){return Optional.ofNullable(redis.opsForValue().get(keys.key("session","temporary",sid)));}
}
