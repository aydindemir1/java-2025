package com.aydindemir.redis.config;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache; import org.springframework.cache.CacheManager; import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration; import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory; import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext; import org.springframework.data.redis.serializer.RedisSerializer; import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
@EnableConfigurationProperties(RedisLearningProperties.class)
public class RedisConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
    @Bean RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory cf){
        RedisTemplate<String,Object> t=new RedisTemplate<>(); t.setConnectionFactory(cf);
        StringRedisSerializer s=new StringRedisSerializer(); RedisSerializer<Object> j=RedisSerializer.json();
        t.setKeySerializer(s); t.setHashKeySerializer(s); t.setValueSerializer(j); t.setHashValueSerializer(j); t.afterPropertiesSet(); return t;
    }
    @Bean CacheManager cacheManager(RedisConnectionFactory cf, RedisLearningProperties p){
        RedisCacheConfiguration c=RedisCacheConfiguration.defaultCacheConfig().entryTtl(p.getProductCacheTtl()).disableCachingNullValues()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        return RedisCacheManager.builder(cf).cacheDefaults(c).build();
    }
    @Bean CacheErrorHandler cacheErrorHandler(){ return new CacheErrorHandler(){
        public void handleCacheGetError(RuntimeException e,Cache c,Object k){log.warn("Cache GET failed cache={} key={}",c.getName(),k,e);} 
        public void handleCachePutError(RuntimeException e,Cache c,Object k,Object v){log.warn("Cache PUT failed cache={} key={}",c.getName(),k,e);} 
        public void handleCacheEvictError(RuntimeException e,Cache c,Object k){log.warn("Cache EVICT failed cache={} key={}",c.getName(),k,e);} 
        public void handleCacheClearError(RuntimeException e,Cache c){log.warn("Cache CLEAR failed cache={}",c.getName(),e);} }; }
}
