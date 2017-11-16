package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "quote_product_lookup", schema = "sale")
public class ProductQuote implements Serializable{


    private ProductQuoteId productQuoteId = new ProductQuoteId();

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="product_id")
    private TempProduct product;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="quote_id")
    private Quote quote;

    @Column
    private Long quantity;

    @Id
    public ProductQuoteId getProductQuoteId() {
        return productQuoteId;
    }

    public void setProductQuoteId(ProductQuoteId productQuoteId) {
        this.productQuoteId = productQuoteId;
    }

//    public TempProduct getTempProduct() {
//        return product;
//    }
//
//    public void setTempProduct(TempProduct product) {
//        this.product = product;
//    }
//
//    public Quote getQuote() {
//        return quote;
//    }
//
//    public void setQuote(Quote quote) {
//        this.quote = quote;
//    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductQuote that = (ProductQuote) o;

        if (getProductQuoteId() != null ? !getProductQuoteId().equals(that.getProductQuoteId())
                : that.getProductQuoteId() != null)
            return false;

        return true;
    }


}