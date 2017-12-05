package com.cylinder.products.model;

import com.cylinder.products.model.ProductRepository;
import com.cylinder.products.model.Product;
import com.cylinder.RespositoryTests;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.util.List;

public class ProductRepositoryTests extends RespositoryTests {

  @Autowired
  ProductRepository productRepository;

  @Before
  public void initData() {
    Product product = new Product();
    product.setName("Box");
    productRepository.save(product);
    product = new Product();
    product.setName("Shirt");
    productRepository.save(product);
    product = new Product();
    product.setName("Pants");
    productRepository.save(product);
  }

  @Test
  public void testExistsBy() {
    Long id = new Long("1");
    boolean isExisting = productRepository.existsByProductId(id);
    assertEquals(isExisting, true);
    id = new Long("4");
    isExisting = productRepository.existsByProductId(id);
    assertEquals(isExisting, false);
  }

  @Test
  public void testDeleteById() {
    Long id = new Long("4");
    boolean isExisting = productRepository.existsByProductId(id);
    assertEquals(isExisting, true);
    productRepository.deleteByProductId(id);
    isExisting = productRepository.existsByProductId(id);
    assertEquals(isExisting, false);
  }

}
