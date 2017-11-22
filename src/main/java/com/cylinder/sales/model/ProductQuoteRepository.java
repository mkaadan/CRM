package com.cylinder.sales.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductQuoteRepository extends CrudRepository<ProductQuote, Long> {
    @Query("SELECT pq FROM ProductQuote pq WHERE pq.quote.quoteId=:quoteId")
    List<ProductQuote> getProductsByQuoteId(@Param("quoteId") Long quoteId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale.quote_product_lookup WHERE quote_id=:quoteId", nativeQuery = true)
    int deleteProductsByQuoteId(@Param("quoteId") Long quoteId);
}
