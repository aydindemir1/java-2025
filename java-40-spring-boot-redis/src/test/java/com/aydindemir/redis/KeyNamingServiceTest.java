package com.aydindemir.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.aydindemir.redis.config.RedisLearningProperties;
import com.aydindemir.redis.redis.cache.KeyNamingService;

class KeyNamingServiceTest {

	@Test
	void buildsConsistentKey() {

		RedisLearningProperties properties = new RedisLearningProperties();
		properties.setKeyPrefix("java40");

		KeyNamingService service = new KeyNamingService(properties);

		String key = service.key("product", "cache", 42);

		assertThat(key).isEqualTo("java40:product:cache:42");
	}
}