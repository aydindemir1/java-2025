package com.aydindemir.redis.model;

import java.time.Instant;

public record TemporarySession(String sessionId, String userId, Instant createdAt) {
}
