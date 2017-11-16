package com.cylinder.product.model ;

import javax.persistence.*;


@Entity
@Table(name="details", schema="product")
public class Product {

  @Id
  @Column(name="product_id")
  protected Long productId;
  @Column(name="code")
  protected String code;
  @Column(name="name")
  protected String name;
  @Column(name="is_active")
  protected boolean isActive;
  @Column(name="category")
  protected String category;
  @Column(name="sales_start")
  protected String salesStart;
  @Column(name="sales_end")
  protected String salesEnd;
  @Column(name="support_start")
  protected String supportStart;
  @Column(name="support_end")
  protected String supportEnd;
  @Column(name="unit_price")
  protected int unitPrice;
  @Column(name="taxable")
  protected boolean taxable;
  @Column(name="commission_rate_percent")
  protected int commissionRatePercent;
  @Column(name="qty_in_stock")
  protected String qtyInStock;
  @Column(name="qty_in_order")
  protected String qtyInOrder;
  @Column(name="qty_in_demand")
  protected String qtyInDemand;
  @Column(name="description")
  protected String description;
  @Column(name="created")
  protected String created;
  @Column(name="last_modified")
  protected String lastModified;
  @Column(name="last_modified_by_id")
  protected String lastModifiedById;

  public Product(){}

  public Long getproductId() {
      return this.productId;
  }

  public String getCode() {
      return this.code;
  }

  public String getName() {
      return this.name;
  }

  public boolean getIsActive() {
      return this.isActive;
  }

  public String getCategory() {
      return this.category;
  }

  public String getSalesStart() {
      return this.salesStart;
  }

  public String getSalesEnd() {
      return this.salesEnd;
  }

  public String getSupportStart() {
      return this.supportStart;
  }

  public String getSupportEnd() {
      return this.supportEnd;
  }

  public int getUnitPrice() {
      return this.unitPrice;
  }

  public boolean getTaxable() {
      return this.taxable;
  }

  public int getCommissionRatePercent() {
      return this.commissionRatePercent;
  }

  public String getQtyInStock() {
      return this.qtyInStock;
  }

  public String getQtyInOrder() {
      return this.qtyInOrder;
  }

  public String getQtyInDemand() {
      return this.qtyInDemand;
  }

  public String getDescription() {
      return this.description;
  }

  public String getCreated() {
      return this.created;
  }

  public String getLastModified() {
      return this.lastModified;
  }

  public String getLastModifiedById() {
      return this.lastModifiedById;
  }

  public void setproductId(Long productId) {
       this.productId = productId;
  }

  public void setCode(String code) {
       this.code = code ;
  }

  public void setName(String name) {
       this.name = name;
  }

  public void setIsActive(boolean isActive) {
       this.isActive = isActive;
  }

  public void setCategory(String category) {
       this.category = category;
  }

  public void setSalesStart(String salesStart) {
       this.salesStart = salesStart;
  }

  public void setSalesEnd(String salesEnd) {
       this.salesEnd = salesEnd;
  }

  public void setSupportStart(String supportStart) {
       this.supportStart = supportStart;
  }

  public void setSupportEnd(String supportEnd) {
       this.supportEnd = supportEnd;
  }

  public void setUnitPrice(int unitPrice){
      this.unitPrice = unitPrice ;
  }

  public void setTaxable(boolean taxable) {
       this.taxable = taxable;
  }

  public void setCommissionRatePercent(int commissionRatePercent) {
       this.commissionRatePercent = commissionRatePercent;
  }

  public void setQtyInStock(String qtyInStock) {
       this.qtyInStock = qtyInStock;
  }

  public void setQtyInOrder(String qtyInOrder) {
       this.qtyInOrder = qtyInOrder;
  }

  public void setQtyInDemand(String qtyinDemand) {
       this.qtyInDemand = qtyinDemand;
  }

  public void setDescription(String description) {
       this.description = description;
  }

  public void setCreated(String created) {
       this.created = created;
  }

  public void setLastModified(String lastModified) {
       this.lastModified = lastModified;
  }

  public void setLastModifiedById(String lastModifiedById) {
       this.lastModifiedById = lastModifiedById;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
