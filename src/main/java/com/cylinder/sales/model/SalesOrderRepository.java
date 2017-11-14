package com.cylinder.sales.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {
}
