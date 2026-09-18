package com.aydindemir.redis.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.redis.cache.KeyNamingService;

@Service
public class UniqueUsersService {
	private final StringRedisTemplate redis;
	private final KeyNamingService keys;

	public UniqueUsersService(StringRedisTemplate r, KeyNamingService k) {
		redis = r;
		keys = k;
	}

	public void record(String p, String u) {
		redis.opsForSet().add(keys.key("analytics", "unique-users", p), u);
	}

	public Long count(String p) {
		return redis.opsForSet().size(keys.key("analytics", "unique-users", p));
	}
}
