package com.aydindemir.redis.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.model.Product;

@Service
public class SerializationDemoService {
	private final RedisTemplate<String, Object> redis;

	public SerializationDemoService(RedisTemplate<String, Object> redis) {
		this.redis = redis;
	}

	public void saveJson(String key, Product p, Duration ttl) {
		redis.opsForValue().set(key, p, ttl);
	}

	public Optional<Object> getJson(String key) {
		return Optional.ofNullable(redis.opsForValue().get(key));
	}
}
