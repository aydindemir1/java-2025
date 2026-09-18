package com.aydindemir.redis.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.redis")
public class RedisLearningProperties {
	private String keyPrefix = "java40";
	private Duration productCacheTtl = Duration.ofMinutes(10);
	private Duration sessionTtl = Duration.ofMinutes(30);
	private Duration idempotencyTtl = Duration.ofMinutes(15);
	private Duration lockTtl = Duration.ofSeconds(10);
	private Duration rateLimitWindow = Duration.ofSeconds(60);
	private long rateLimitMaxRequests = 10;

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String v) {
		keyPrefix = v;
	}

	public Duration getProductCacheTtl() {
		return productCacheTtl;
	}

	public void setProductCacheTtl(Duration v) {
		productCacheTtl = v;
	}

	public Duration getSessionTtl() {
		return sessionTtl;
	}

	public void setSessionTtl(Duration v) {
		sessionTtl = v;
	}

	public Duration getIdempotencyTtl() {
		return idempotencyTtl;
	}

	public void setIdempotencyTtl(Duration v) {
		idempotencyTtl = v;
	}

	public Duration getLockTtl() {
		return lockTtl;
	}

	public void setLockTtl(Duration v) {
		lockTtl = v;
	}

	public Duration getRateLimitWindow() {
		return rateLimitWindow;
	}

	public void setRateLimitWindow(Duration v) {
		rateLimitWindow = v;
	}

	public long getRateLimitMaxRequests() {
		return rateLimitMaxRequests;
	}

	public void setRateLimitMaxRequests(long v) {
		rateLimitMaxRequests = v;
	}
}
