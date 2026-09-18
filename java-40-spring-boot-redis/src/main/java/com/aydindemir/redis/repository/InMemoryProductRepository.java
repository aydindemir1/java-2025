package com.aydindemir.redis.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.aydindemir.redis.model.Product;

@Repository
public class InMemoryProductRepository implements ProductRepository {
	private final Map<Long, Product> data = new ConcurrentHashMap<>();
	private final AtomicLong loads = new AtomicLong();

	public InMemoryProductRepository() {
		data.put(1L, new Product(1L, "Mechanical Keyboard", new BigDecimal("2500.00")));
		data.put(2L, new Product(2L, "Developer Mouse", new BigDecimal("1600.00")));
	}

	public Optional<Product> findById(Long id) {
		loads.incrementAndGet();
		return Optional.ofNullable(data.get(id));
	}

	public Product save(Product p) {
		data.put(p.id(), p);
		return p;
	}

	public void deleteById(Long id) {
		data.remove(id);
	}

	public long loadCount() {
		return loads.get();
	}
}
