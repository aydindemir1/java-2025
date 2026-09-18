package com.aydindemir.redis.redis.cache;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.exception.ResourceNotFoundException;
import com.aydindemir.redis.model.Product;
import com.aydindemir.redis.redis.lock.DistributedLockService;
import com.aydindemir.redis.repository.ProductRepository;

@Service
public class StampedeProtectedProductService {
	private final RedisTemplate<String, Object> redis;
	private final DistributedLockService locks;
	private final ProductRepository repo;
	private final KeyNamingService keys;

	public StampedeProtectedProductService(RedisTemplate<String, Object> r, DistributedLockService l,
			ProductRepository repo, KeyNamingService k) {
		redis = r;
		locks = l;
		this.repo = repo;
		keys = k;
	}

	public Object get(Long id) {
		String cacheKey = keys.key("product", "manual-cache", id);
		Object cached = redis.opsForValue().get(cacheKey);
		if (cached != null)
			return cached;
		String lockKey = keys.key("lock", "product", id);
		Optional<String> token = locks.tryLock(lockKey, Duration.ofSeconds(5));
		if (token.isEmpty())
			throw new IllegalStateException("Cache rebuild already in progress for product " + id);
		try {
			Object second = redis.opsForValue().get(cacheKey);
			if (second != null)
				return second;
			Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
			redis.opsForValue().set(cacheKey, p, Duration.ofMinutes(10));
			return p;
		} finally {
			locks.unlock(lockKey, token.get());
		}
	}
}
