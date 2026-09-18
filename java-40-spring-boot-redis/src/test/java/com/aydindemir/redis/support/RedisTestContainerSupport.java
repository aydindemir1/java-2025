package com.aydindemir.redis.support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class RedisTestContainerSupport {
	@Container
	static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:8.2-alpine"))
			.withExposedPorts(6379);

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		r.add("spring.data.redis.host", REDIS::getHost);
		r.add("spring.data.redis.port", () -> REDIS.getMappedPort(6379));
	}
}
