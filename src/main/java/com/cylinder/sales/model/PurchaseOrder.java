package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "purchase_orders", schema = "sale")
public class PurchaseOrder extends SimpleAudit{
    /** The identifyer of the  purchase order. */
    @Id
    @Getter
    @Setter
    @Column(name="purchase_order_id")
    private Long purchaseOrderId;

    /** The identifyer of the contact associated with the purchase order. */
    @Getter
    @Setter
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the purchase order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The owner associated with the purchase order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    /** The set of products for the purchase order. */
    @Getter
    @Setter
    @OneToMany(mappedBy = "purchaseOrder")
    private Set<ProductPurchaseOrder> productPurchaseOrder;

    public PurchaseOrder() { }    
}
