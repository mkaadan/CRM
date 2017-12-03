package com.cylinder.sales.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductQuoteRepository extends CrudRepository<ProductQuote, Long> {
    /**
     * Finds the products associated with a quote id.
     *
     * @param quoteId The id of the quote record one wishes to geet the products for.
     * @return List of productquotes
     */
    @Query("SELECT pq FROM ProductQuote pq WHERE pq.quote.quoteId=:quoteId")
    List<ProductQuote> getProductsByQuoteId(@Param("quoteId") Long quoteId);

    /**
     * Delete the product quotes based upon the quote id.
     *
     * @param quoteId the id of the quote record one wishes to delete the products from.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale.quote_product_lookup WHERE quote_id=:quoteId", nativeQuery = true)
    int deleteProductsByQuoteId(@Param("quoteId") Long quoteId);
}
