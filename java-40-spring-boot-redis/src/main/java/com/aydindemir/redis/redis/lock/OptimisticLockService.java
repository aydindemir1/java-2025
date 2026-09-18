package com.aydindemir.redis.redis.lock;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockService {
	private static final int MAX_RETRIES = 5;
	private final StringRedisTemplate redis;

	public OptimisticLockService(StringRedisTemplate r) {
		redis = r;
	}

	public boolean compareAndIncrement(String key) {
		for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
			Boolean ok = redis.execute((RedisCallback<Boolean>) c -> {
				byte[] k = b(key);
				c.watch(k);
				byte[] cur = c.stringCommands().get(k);
				long n = cur == null ? 0 : Long.parseLong(new String(cur, StandardCharsets.UTF_8));
				c.multi();
				c.stringCommands().set(k, b(Long.toString(n + 1)));
				List<Object> result = c.exec();
				return result != null && !result.isEmpty();
			});
			if (Boolean.TRUE.equals(ok))
				return true;
		}
		return false;
	}

	private byte[] b(String v) {
		return v.getBytes(StandardCharsets.UTF_8);
	}
}
