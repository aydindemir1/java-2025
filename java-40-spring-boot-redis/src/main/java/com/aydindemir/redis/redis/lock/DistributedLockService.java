package com.aydindemir.redis.redis.lock;
import java.time.Duration; import java.util.*; import org.springframework.data.redis.core.StringRedisTemplate; import org.springframework.data.redis.core.script.DefaultRedisScript; import org.springframework.stereotype.Service;
@Service public class DistributedLockService {
 private static final DefaultRedisScript<Long> RELEASE_SCRIPT=new DefaultRedisScript<>("if redis.call('GET', KEYS[1]) == ARGV[1] then return redis.call('DEL', KEYS[1]) end return 0",Long.class);
 private final StringRedisTemplate redis; public DistributedLockService(StringRedisTemplate r){redis=r;}
 public Optional<String> tryLock(String key,Duration ttl){String token=UUID.randomUUID().toString();Boolean ok=redis.opsForValue().setIfAbsent(key,token,ttl);return Boolean.TRUE.equals(ok)?Optional.of(token):Optional.empty();}
 public boolean unlock(String key,String token){Long n=redis.execute(RELEASE_SCRIPT,List.of(key),token);return n!=null&&n==1L;}
}
