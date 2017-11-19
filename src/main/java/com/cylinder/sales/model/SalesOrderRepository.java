package com.cylinder.sales.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {
    /**
     * Check if a certain sales order record exists.
     * @param leadId The id of the lead record one wishes to comfirm its existence.
     * @return does the record exist?
     */
    @Query(value="SELECT exists(SELECT 1 FROM sale.salesOrders WHERE sales_order_id=:salesOrderId)", nativeQuery=true)
    boolean existsById(@Param("salesOrderId")Long salesOrderId);

    /**
     * Delete a sales order record based upon its id.
     * @param leadId the id of the lead record one wishes to delete.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value="DELETE FROM sale.salesOrders WHERE sales_order_id=:salesOrderId", nativeQuery=true)
    int deleteById(@Param("salesOrderId") Long salesOrderId);
}