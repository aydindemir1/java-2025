package com.aydindemir.redis.redis.stream;
import java.nio.charset.StandardCharsets; import java.util.List; import org.springframework.data.redis.core.*; import org.springframework.stereotype.Service;
@Service public class PipelineService { private final StringRedisTemplate redis; public PipelineService(StringRedisTemplate r){redis=r;}
 public List<Object> writeBatch(String prefix,int count){return redis.executePipelined((RedisCallback<Object>) c->{for(int i=1;i<=count;i++)c.stringCommands().set(b(prefix+":"+i),b("value-"+i));return null;});}
 private byte[] b(String v){return v.getBytes(StandardCharsets.UTF_8);} }
