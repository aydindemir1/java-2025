package com.aydindemir.redis.repository;
import java.util.Optional; import com.aydindemir.redis.model.Product;
public interface ProductRepository { Optional<Product> findById(Long id); Product save(Product product); void deleteById(Long id); long loadCount(); }
