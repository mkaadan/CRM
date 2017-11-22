package com.cylinder.products.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ProductRepository extends CrudRepository<Product, Long> {
    /**
     * Check if a certain product record exists.
     *
     * @param productId The id of the product record one wishes to comfirm its existence.
     * @return does the record exist?
     */
    @Query(value = "SELECT exists(SELECT 1 FROM product.details WHERE product_id=:productId)", nativeQuery = true)
    boolean existsByProductId(@Param("productId") Long productId);

    /**
     * Delete a product record based upon its id.
     *
     * @param productId the id of the product record one wishes to delete.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product.details WHERE product_id=:productId", nativeQuery = true)
    int deleteByProductId(@Param("productId") Long productId);
}
