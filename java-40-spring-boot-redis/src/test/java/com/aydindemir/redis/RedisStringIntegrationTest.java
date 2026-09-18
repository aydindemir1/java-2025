package com.aydindemir.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aydindemir.redis.service.RedisStringService;
import com.aydindemir.redis.support.RedisTestContainerSupport;

@SpringBootTest
class RedisStringIntegrationTest extends RedisTestContainerSupport {

	@Autowired
	RedisStringService redisStringService;

	@Test
	void setGetExistsDelete() {

		redisStringService.set("test:string", "hello", null);

		assertThat(redisStringService.get("test:string")).contains("hello");

		assertThat(redisStringService.exists("test:string")).isTrue();

		redisStringService.delete("test:string");

		assertThat(redisStringService.exists("test:string")).isFalse();
	}

	@Test
	void ttl() {

		redisStringService.set("test:ttl", "value", null);

		redisStringService.expire("test:ttl", Duration.ofSeconds(30));

		assertThat(redisStringService.ttl("test:ttl")).isBetween(1L, 30L);
	}
}