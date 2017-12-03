package com.cylinder.sales.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {
    /**
     * Check if a certain sales order record exists.
     *
     * @param quoteId The id of the lead record one wishes to comfirm its existence.
     * @return does the record exist?
     */
    @Query(value = "SELECT exists(SELECT 1 FROM sale.sales_orders WHERE sales_order_id=:salesOrderId)", nativeQuery = true)
    boolean existsById(@Param("salesOrderId") Long salesOrderId);

    /**
     * Delete a quote record based upon its id.
     *
     * @param quoteId the id of the lead record one wishes to delete.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale.sales_orders WHERE sales_order_id=:salesOrderId", nativeQuery = true)
    int deleteById(@Param("salesOrderId") Long salesOrderId);
}
