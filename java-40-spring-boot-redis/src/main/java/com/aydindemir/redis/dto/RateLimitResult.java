package com.aydindemir.redis.dto;

public record RateLimitResult(boolean allowed, long currentCount, long limit, long retryAfterSeconds) {
}