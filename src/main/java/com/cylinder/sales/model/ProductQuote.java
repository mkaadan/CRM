package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "quote_product_lookup", schema = "sale")
public class ProductQuote implements Serializable{

    @Id
    private ProductQuoteId productQuoteId = new ProductQuoteId();


    public ProductQuoteId getProductQuoteId() {
        return productQuoteId;
    }

    public void setProductQuoteId(ProductQuoteId productQuoteId) {
        this.productQuoteId = productQuoteId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    public TempProduct getProduct(){
        return getProductQuoteId().getTempProduct();
    }

    @ManyToOne
    @JoinColumn(name = "quote_id")
    public Quote getQuote(){
        return getProductQuoteId().getQuote();
    }

    public void setProduct(TempProduct tempProduct){
        getProductQuoteId().setTempProduct(tempProduct);
    }

    public void setQuote(Quote quote){
        getProductQuoteId().setQuote(quote);
    }

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