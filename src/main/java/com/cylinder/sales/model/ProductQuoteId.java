package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import java.io.Serializable;

public class ProductQuoteId implements Serializable{

    private TempProduct tempProduct;

    private Quote quote;

    public ProductQuoteId() {
        super();
    }

    public TempProduct getTempProduct() {
        return tempProduct;
    }

    public void setTempProduct(TempProduct tempProduct) {
        this.tempProduct = tempProduct;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

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
}