package com.cylinder.sales.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<Status, Long> {
}
