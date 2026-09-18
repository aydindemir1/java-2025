package com.aydindemir.redis.redis.stream;
import java.nio.charset.StandardCharsets; import java.util.List; import org.springframework.data.redis.core.*; import org.springframework.stereotype.Service;
@Service public class TransactionService { private final StringRedisTemplate redis; public TransactionService(StringRedisTemplate r){redis=r;}
 public List<Object> setTwoAtomically(String k1,String v1,String k2,String v2){return redis.execute((RedisCallback<List<Object>>) c->{c.multi();c.stringCommands().set(b(k1),b(v1));c.stringCommands().set(b(k2),b(v2));return c.exec();});}
 private byte[] b(String v){return v.getBytes(StandardCharsets.UTF_8);} }
