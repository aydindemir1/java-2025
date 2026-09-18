package com.aydindemir.redis.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydindemir.redis.config.RedisLearningProperties;
import com.aydindemir.redis.dto.RateLimitResult;
import com.aydindemir.redis.dto.TransactionRequest;
import com.aydindemir.redis.redis.cache.HotKeyDetectorService;
import com.aydindemir.redis.redis.cache.ScanService;
import com.aydindemir.redis.redis.lock.DistributedLockService;
import com.aydindemir.redis.redis.lock.OptimisticLockService;
import com.aydindemir.redis.redis.ratelimit.IdempotencyService;
import com.aydindemir.redis.redis.ratelimit.RateLimiterService;
import com.aydindemir.redis.redis.stream.PipelineService;
import com.aydindemir.redis.redis.stream.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/advanced")
@Tag(name = "06 - Transactions / Pipeline / Lock / Rate Limit / SCAN")
public class RedisAdvancedController {
	private final TransactionService tx;
	private final PipelineService pipe;
	private final OptimisticLockService optimistic;
	private final DistributedLockService lock;
	private final RateLimiterService rate;
	private final IdempotencyService idem;
	private final ScanService scan;
	private final HotKeyDetectorService hot;
	private final RedisLearningProperties p;

	public RedisAdvancedController(TransactionService a, PipelineService b, OptimisticLockService c,
			DistributedLockService d, RateLimiterService e, IdempotencyService f, ScanService g,
			HotKeyDetectorService h, RedisLearningProperties p) {
		tx = a;
		pipe = b;
		optimistic = c;
		lock = d;
		rate = e;
		idem = f;
		scan = g;
		hot = h;
		this.p = p;
	}

	@PostMapping("/transaction")
	public Object tx(@Valid @RequestBody TransactionRequest r) {
		return tx.setTwoAtomically(r.key1(), r.value1(), r.key2(), r.value2());
	}

	@PostMapping("/pipeline/{prefix}")
	public Object pipeline(@PathVariable String prefix, @RequestParam(defaultValue = "100") int count) {
		return Map.of("responses", pipe.writeBatch(prefix, count).size());
	}

	@PostMapping("/optimistic-lock/{key}")
	public Map<String, Boolean> optimistic(@PathVariable String key) {
		return Map.of("updated", optimistic.compareAndIncrement(key));
	}

	@PostMapping("/distributed-lock/{name}")
	public Object acquire(@PathVariable String name) {
		String key = "java40:lock:" + name;
		return lock.tryLock(key, p.getLockTtl())
				.<Object>map(token -> Map.of("acquired", true, "key", key, "token", token))
				.orElseGet(() -> Map.of("acquired", false, "key", key));
	}

	@DeleteMapping("/distributed-lock/{name}")
	public Map<String, Boolean> release(@PathVariable String name, @RequestParam String token) {
		return Map.of("released", lock.unlock("java40:lock:" + name, token));
	}

	@PostMapping("/rate-limit/{clientId}")
	public RateLimitResult rl(@PathVariable String clientId) {
		return rate.check("java40:rate-limit:" + clientId, p.getRateLimitMaxRequests(), p.getRateLimitWindow());
	}

	@PostMapping("/idempotency/{requestId}")
	public Map<String, Boolean> idem(@PathVariable String requestId) {
		return Map.of("firstRequest", idem.register("java40:idempotency:" + requestId, p.getIdempotencyTtl()));
	}

	@GetMapping("/scan")
	public Object scan(@RequestParam(defaultValue = "java40:*") String pattern,
			@RequestParam(defaultValue = "100") long count) {
		return scan.scan(pattern, count);
	}

	@PostMapping("/hot-key/{logicalKey}")
	public void hot(@PathVariable String logicalKey) {
		hot.recordAccess(logicalKey);
	}

	@GetMapping("/hot-key")
	public Object hot(@RequestParam(defaultValue = "10") long count) {
		return hot.top(count);
	}
}
