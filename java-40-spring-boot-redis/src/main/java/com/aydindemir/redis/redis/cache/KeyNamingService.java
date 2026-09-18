package com.aydindemir.redis.redis.cache;
import org.springframework.stereotype.Component; import com.aydindemir.redis.config.RedisLearningProperties;
@Component public class KeyNamingService { private final RedisLearningProperties p; public KeyNamingService(RedisLearningProperties p){this.p=p;}
 public String key(String domain,String purpose,Object id){return "%s:%s:%s:%s".formatted(p.getKeyPrefix(),domain,purpose,id);} public String key(String domain,String purpose){return "%s:%s:%s".formatted(p.getKeyPrefix(),domain,purpose);} }
