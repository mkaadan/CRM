package com.cylinder.sales.model;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "sale_product_lookups", schema = "sale")
public class ProductSalesOrder implements Serializable{

    /** The identifyer of the sales order-product relation. */
    @Getter
    @Setter
    @Id
    @Column(name="sale_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesOrderProductId;

    /** The identifyer of the product. */
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="product_id")
    private TempProduct product;

    /** The identifyer of the sales order. */
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="sales_order_id")
    private SalesOrder salesOrder;

    /** The quantity of the product on the sales order. */
    @Getter
    @Setter
    @Column
    private Long quantity;

    /** The discount on the product on the sales order. */
    @Getter
    @Setter
    @Column
    private float discount;

//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        ProductQuote that = (ProductQuote) o;
//
//        if (getProductQuoteId() != null ? !getProductQuoteId().equals(that.getProductQuoteId())
//                : that.getProductQuoteId() != null)
//            return false;
//
//        return true;
//    }


}