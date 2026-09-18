package com.aydindemir.redis.service;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.redis.cache.KeyNamingService;

@Service
public class RecentItemsService {
	private static final long MAX = 20;
	private final StringRedisTemplate redis;
	private final KeyNamingService keys;

	public RecentItemsService(StringRedisTemplate r, KeyNamingService k) {
		redis = r;
		keys = k;
	}

	public void add(String u, String item) {
		String key = keys.key("recent", "items", u);
		redis.opsForList().remove(key, 0, item);
		redis.opsForList().leftPush(key, item);
		redis.opsForList().trim(key, 0, MAX - 1);
	}

	public List<String> get(String u) {
		return redis.opsForList().range(keys.key("recent", "items", u), 0, MAX - 1);
	}
}
