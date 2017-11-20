package com.cylinder.products.model ;

import javax.persistence.*;
import lombok.*;
import com.cylinder.shared.model.SimpleAudit;
import java.sql.Date;
import javax.validation.constraints.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;



@Entity
@Table(name="details", schema="product")
public class Product extends SimpleAudit {

  @Getter
  @Setter
  @Id
  @Column(name="product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long productId;

  @Getter
  @Setter
  @Column(name="code")
  @Pattern(regexp="([a-zA-Z0-9]{1,250})", message="Please enter a valid code.")
  protected String code;

  @Getter
  @Setter
  @Column(name="name")
  @Pattern(regexp="(.{1,250})", message="Please enter a valid name.")
  protected String name;

  @Getter
  @Setter
  @Column(name="is_active")
  protected Boolean isActive;

  @Getter
  @Setter
  @Column(name="category")
  @Pattern(regexp="([a-zA-Z]{1,250})", message="Please enter a valid category.")
  protected String category;

  @Getter
  @Setter
  @Column(name="sales_start")
  protected Date salesStart;

  @Getter
  @Setter
  @Column(name="sales_end")
  protected Date salesEnd;

  @Getter
  @Setter
  @Column(name="support_start")
  protected Date supportStart;

  @Getter
  @Setter
  @Column(name="support_end")
  protected Date supportEnd;

  @Getter
  @Setter
  @Column(name="unit_price")
  protected Integer unitPrice;

  @Getter
  @Setter
  @Column(name="taxable")
  protected Boolean taxable;

  @Getter
  @Setter
  @Column(name="commission_rate_percent")
  protected Integer commissionRatePercent;

  @Getter
  @Setter
  @Column(name="qty_in_stock")
  @Pattern(regexp="([0-9]{1,250})", message="Please enter a valid Quantity.")
  protected String qtyInStock;

  @Getter
  @Setter
  @Column(name="qty_in_order")
  @Pattern(regexp="([0-9]{1,250})", message="Please enter a valid Quantity.")
  protected String qtyInOrder;

  @Getter
  @Setter
  @Column(name="qty_in_demand")
  @Pattern(regexp="([0-9]{1,250})", message="Please enter a valid Quantity.")
  protected String qtyInDemand;

  @Getter
  @Setter
  @Column(name="description")
  protected String description;


  public Product(){}


}
