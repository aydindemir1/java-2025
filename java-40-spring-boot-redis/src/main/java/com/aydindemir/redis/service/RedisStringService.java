package com.aydindemir.redis.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisStringService {
	private final StringRedisTemplate redis;

	public RedisStringService(StringRedisTemplate redis) {
		this.redis = redis;
	}

	public void set(String k, String v, Long ttl) {
		if (ttl == null)
			redis.opsForValue().set(k, v);
		else
			redis.opsForValue().set(k, v, Duration.ofSeconds(ttl));
	}

	public Optional<String> get(String k) {
		return Optional.ofNullable(redis.opsForValue().get(k));
	}

	public boolean delete(String k) {
		return Boolean.TRUE.equals(redis.delete(k));
	}

	public boolean exists(String k) {
		return Boolean.TRUE.equals(redis.hasKey(k));
	}

	public boolean expire(String k, Duration d) {
		return Boolean.TRUE.equals(redis.expire(k, d));
	}

	public Long ttl(String k) {
		return redis.getExpire(k);
	}

	public Long increment(String k) {
		return redis.opsForValue().increment(k);
	}

	public Long decrement(String k) {
		return redis.opsForValue().decrement(k);
	}
}
