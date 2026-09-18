package com.aydindemir.redis.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aydindemir.redis.dto.ProductRequest;
import com.aydindemir.redis.model.Product;
import com.aydindemir.redis.redis.cache.ProductCacheService;
import com.aydindemir.redis.redis.cache.StampedeProtectedProductService;
import com.aydindemir.redis.service.SerializationDemoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "03 - Spring Cache & Serialization")
public class ProductCacheController {
	private final ProductCacheService cache;
	private final SerializationDemoService serial;
	private final StampedeProtectedProductService stampede;

	public ProductCacheController(ProductCacheService c, SerializationDemoService s,
			StampedeProtectedProductService p) {
		cache = c;
		serial = s;
		stampede = p;
	}

	@GetMapping("/{id}")
	public Product get(@PathVariable Long id) {
		return cache.get(id);
	}

	@PutMapping
	public Product save(@Valid @RequestBody ProductRequest r) {
		return cache.save(new Product(r.id(), r.name(), r.price()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		cache.delete(id);
	}

	@GetMapping("/metrics/repository-load-count")
	public Map<String, Long> count() {
		return Map.of("loadCount", cache.repositoryLoadCount());
	}

	@PutMapping("/{id}/json-demo")
	public void json(@PathVariable Long id) {
		serial.saveJson("java40:serialization:product:" + id, cache.get(id), Duration.ofMinutes(5));
	}

	@GetMapping("/{id}/stampede-protected")
	public Object protectedGet(@PathVariable Long id) {
		return stampede.get(id);
	}
}
