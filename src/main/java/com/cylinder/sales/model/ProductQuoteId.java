package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Embeddable
public class ProductQuoteId implements Serializable{

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="product_id")
    private TempProduct tempProduct;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="quote_id")
    private Quote quote;

    public ProductQuoteId() {
        super();
    }

//    @ManyToOne
//    @Column(name="product_id")
//    public TempProduct getTempProduct() {
//        return tempProduct;
//    }
//
//    public void setTempProduct(TempProduct tempProduct) {
//        this.tempProduct = tempProduct;
//    }
//
//    @ManyToOne
//    @Column(name="quote_id")
//    public Quote getQuote() {
//        return quote;
//    }
//
//    public void setQuote(Quote quote) {
//        this.quote = quote;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductQuoteId other = (ProductQuoteId) obj;
        if (tempProduct == null) {
            if (other.tempProduct != null)
                return false;
        } else if (!tempProduct.equals(other.tempProduct))
            return false;
        if (quote == null) {
            if (other.quote != null)
                return false;
        } else if (!quote.equals(other.quote))
            return false;
        return true;
    }

    public int hashCode() {
        int result;
        result = (tempProduct != null ? tempProduct.hashCode() : 0);
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        return result;
    }
}