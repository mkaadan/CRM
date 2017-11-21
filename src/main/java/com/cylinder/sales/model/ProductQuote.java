package com.cylinder.sales.model;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

import com.cylinder.products.model.Product;

@Entity
@Table(name = "quote_product_lookup", schema = "sale")
public class ProductQuote implements Serializable{

    /** The identifyer of the quote-product relation. */
    @Getter
    @Setter
    @Id
    @Column(name="quote_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteProductId;

    /** The identifyer of the product. */
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    /** The identifyer of the quote. */
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="quote_id")
    private Quote quote;

    /** The quantity of the product on the quote. */
    @Getter
    @Setter
    @Column
    private Long quantity;

    /** The discount on the product on the quote. */
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
