package com.cylinder.sales.model.forms;

import com.cylinder.sales.model.SalesOrder;
import com.cylinder.sales.model.ProductSalesOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class SalesOrderForm {
  @Getter
  @Setter
  @Valid
  private SalesOrder salesOrder;
  @Getter
  @Setter
  @Valid
  private List<ProductSalesOrder> productList = new ArrayList<ProductSalesOrder>();

  public SalesOrderForm() {}

  public SalesOrderForm(SalesOrder salesOrder, List<ProductSalesOrder> productList) {
    this.salesOrder = salesOrder;
    this.productList = productList;
  }
}
