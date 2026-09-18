package com.aydindemir.redis.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.redis.cache.KeyNamingService;

@Service
public class CounterService {
	private final StringRedisTemplate redis;
	private final KeyNamingService keys;

	public CounterService(StringRedisTemplate r, KeyNamingService k) {
		redis = r;
		keys = k;
	}

	public Long increment(String n) {
		return redis.opsForValue().increment(keys.key("counter", n));
	}

	public Long decrement(String n) {
		return redis.opsForValue().decrement(keys.key("counter", n));
	}
}
