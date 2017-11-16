package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "details", schema = "product")
public class TempProduct implements Serializable{
    @Id
    @Column(name="product_id")
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TempProduct))
            return false;
        final TempProduct other = (TempProduct) obj;
        if (productId != other.getProductId())
            return false;
        return true;
    }
}