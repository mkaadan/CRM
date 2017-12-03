package com.cylinder.sales.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductSalesOrderRepository extends CrudRepository<ProductSalesOrder, Long> {
    @Query("SELECT pso FROM ProductSalesOrder pso WHERE pso.salesOrder.salesOrderId=:salesOrderId")
    List<ProductSalesOrder> getProductsBySalesOrderId(@Param("salesOrderId") Long salesOrderId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sale.sale_product_lookups WHERE sales_order_id=:salesOrderId", nativeQuery = true)
    int deleteProductsBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
}
