package com.aydindemir.redis.redis.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aydindemir.redis.exception.ResourceNotFoundException;
import com.aydindemir.redis.model.Product;
import com.aydindemir.redis.repository.ProductRepository;

@Service
public class ProductCacheService {
	public static final String CACHE_NAME = "products";
	private final ProductRepository repo;

	public ProductCacheService(ProductRepository repo) {
		this.repo = repo;
	}

	@Cacheable(cacheNames = CACHE_NAME, key = "#id")
	public Product get(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
	}

	@CachePut(cacheNames = CACHE_NAME, key = "#product.id()")
	public Product save(Product product) {
		return repo.save(product);
	}

	@CacheEvict(cacheNames = CACHE_NAME, key = "#id")
	public void delete(Long id) {
		repo.deleteById(id);
	}

	public long repositoryLoadCount() {
		return repo.loadCount();
	}
}
