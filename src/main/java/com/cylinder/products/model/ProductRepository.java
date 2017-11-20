package com.cylinder.products.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
  boolean existsByProductId(Long productId);
}
