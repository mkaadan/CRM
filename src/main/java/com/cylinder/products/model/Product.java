package com.cylinder.products.model;

import com.cylinder.shared.model.SimpleAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Table(name = "details", schema = "product")
public class Product extends SimpleAudit {

    @Getter
    @Setter
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long productId;

    @Getter
    @Setter
    @Column(name = "code")
    @Pattern(regexp = "([a-zA-Z0-9]{1,250})", message = "Please enter a valid code.")
    protected String code;

    @Getter
    @Setter
    @Column(name = "name")
    @Pattern(regexp = "(.{1,250})", message = "Please enter a valid name.")
    protected String name;

    @Getter
    @Setter
    @Column(name = "is_active")
    protected Boolean isActive;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    protected Categories category;

    @Getter
    @Setter
    @Column(name = "sales_start")
    protected Date salesStart;

    @Getter
    @Setter
    @Column(name = "sales_end")
    protected Date salesEnd;

    @Getter
    @Setter
    @Column(name = "support_start")
    protected Date supportStart;

    @Getter
    @Setter
    @Column(name = "support_end")
    protected Date supportEnd;

    @Getter
    @Setter
    @Column(name = "unit_price")
    protected BigDecimal unitPrice;

    @Getter
    @Setter
    @Column(name = "taxable")
    protected Boolean taxable;

    @Getter
    @Setter
    @Column(name = "commission_rate_percent")
    protected Integer commissionRatePercent;

    @Getter
    @Setter
    @Column(name = "qty_in_stock")
    protected Integer qtyInStock;

    @Getter
    @Setter
    @Column(name = "qty_in_order")
    protected Integer qtyInOrder;

    @Getter
    @Setter
    @Column(name = "qty_in_demand")
    protected Integer qtyInDemand;

    @Getter
    @Setter
    @Column(name = "description")
    protected String description;


    public Product() {
    }


}
