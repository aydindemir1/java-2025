package com.aydindemir.redis.redis.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScanService {
	private final StringRedisTemplate redis;

	public ScanService(StringRedisTemplate redis) {
		this.redis = redis;
	}

	public List<String> scan(String pattern, long count) {
		ScanOptions o = ScanOptions.scanOptions().match(pattern).count(count).build();
		List<String> keys = new ArrayList<>();
		try (Cursor<String> c = redis.scan(o)) {
			c.forEachRemaining(keys::add);
		}
		return keys;
	}
}
