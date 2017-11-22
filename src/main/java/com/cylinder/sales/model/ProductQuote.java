package com.cylinder.sales.model;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @DecimalMax(value="0.99",message="Please enter a valid discount.")
    @DecimalMin(value="0.00",message="Please enter a valid discount.")
    private float discount;
}
