package com.aydindemir.redis.redis.ratelimit;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.dto.RateLimitResult;

@Service
public class RateLimiterService {
	private static final DefaultRedisScript<Long> SCRIPT = new DefaultRedisScript<>(
			"local current=redis.call('INCR',KEYS[1]); if current==1 then redis.call('EXPIRE',KEYS[1],ARGV[1]) end; if current>tonumber(ARGV[2]) then return -current end; return current",
			Long.class);
	private final StringRedisTemplate redis;

	public RateLimiterService(StringRedisTemplate r) {
		redis = r;
	}

	public RateLimitResult check(String key, long max, Duration window) {
		Long result = redis.execute(SCRIPT, List.of(key), Long.toString(window.toSeconds()), Long.toString(max));
		long raw = result == null ? 0 : result;
		boolean allowed = raw >= 0;
		long count = Math.abs(raw);
		Long ttl = redis.getExpire(key);
		long retry = ttl == null || ttl < 0 ? window.toSeconds() : ttl;
		return new RateLimitResult(allowed, count, max, retry);
	}
}
