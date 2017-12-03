package com.cylinder.sales.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductSalesOrderRepository extends CrudRepository<ProductSalesOrder, Long> {
    /**
     * Finds the products associated with a sales order id.
     *
     * @param salesOrderId The id of the sales order record one wishes to geet the products for.
     * @return List of productSalesOrders.
     */
    @Query("SELECT pso FROM ProductSalesOrder pso WHERE pso.salesOrder.salesOrderId=:salesOrderId")
    List<ProductSalesOrder> getProductsBySalesOrderId(@Param("salesOrderId") Long salesOrderId);

    /**
     * Delete the product sales orders based upon the sales order id.
     *
     * @param salesOrderId the id of the sales order record one wishes to delete the products from.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale.sale_product_lookups WHERE sales_order_id=:salesOrderId", nativeQuery = true)
    int deleteProductsBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
}
