package com.cylinder.sales.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


public interface ProductQuoteRepository extends CrudRepository<ProductQuote, Long> {
  @Query("SELECT pq FROM ProductQuote pq WHERE pq.quote.quoteId=:quoteId")
  List<ProductQuote> getProductsByQuoteId(@Param("quoteId") Long quoteId);

  @Transactional
  @Modifying
  @Query(value="DELETE FROM sale.quote_product_lookup WHERE quote_id=:quoteId", nativeQuery=true)
  int deleteProductsByQuoteId(@Param("quoteId") Long quoteId);
  // 
  // @Transactional
  // @Modifying
  // @Query(value="DELETE FROM sale.quote_product_lookup WHERE quote_id=:quoteId", nativeQuery=true)
  // int deleteProductsByQuoteId(@Param("quoteId") Long quoteId);
}
