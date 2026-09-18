package com.aydindemir.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import com.aydindemir.redis.model.Product;
import com.aydindemir.redis.redis.cache.ProductCacheService;
import com.aydindemir.redis.support.RedisTestContainerSupport;

@SpringBootTest
class ProductCacheIntegrationTest extends RedisTestContainerSupport {

	@Autowired
	ProductCacheService productCacheService;

	@Autowired
	CacheManager cacheManager;

	@Test
	void secondReadHitsCache() {

		cacheManager.getCache(ProductCacheService.CACHE_NAME).clear();

		long before = productCacheService.repositoryLoadCount();

		System.out.println("before = " + before);

		Product firstProduct = productCacheService.get(1L);

		long first = productCacheService.repositoryLoadCount();

		System.out.println("first product = " + firstProduct);
		System.out.println("after first call = " + first);

		Object cachedValue = cacheManager.getCache(ProductCacheService.CACHE_NAME).get(1L);

		System.out.println("cached value = " + cachedValue);

		Product secondProduct = productCacheService.get(1L);

		long second = productCacheService.repositoryLoadCount();

		System.out.println("second product = " + secondProduct);
		System.out.println("after second call = " + second);

		assertThat(first).isEqualTo(before + 1);

		assertThat(second).isEqualTo(first);
	}
}