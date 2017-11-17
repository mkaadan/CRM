package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.accounts.model.Account;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "purchase_orders", schema = "sale")
public class PurchaseOrder {
    @Id
    @Column(name="purchase_order_id")
    private Long purchaseOrderId;

    /** The identifyer of the contact associated with the purchase order. */
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the purchase order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The owner associated with the purchase order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    /** The identifyer of the creator of the purchase order. */
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the purchase order was created. */
    @Column(name="created")
    private Timestamp created;

    /** The time stamp of when the purchase order was last modified. */
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the purchase order. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    /** The set of products for the purchase order. */
    @OneToMany(mappedBy = "purchaseOrder")
    private Set<ProductPurchaseOrder> productPurchaseOrder;

    public PurchaseOrder() { }

    /* Get the identifier for the purchase order. */
    public Long getPurchaseOrderId() {
        return this.purchaseOrderId;
    }

    /* Get the identifier for the contact on the purchase order. */
    public Long getContactId() {
        return this.contactId;
    }

    /* Get the account on the purchase order. */
    public Account getAccount() { return this.account; }

    /* Get the owner of the purchase order. */
    public CrmUser getOwner() { return this.owner; }

    /* Get the identifier for the creator of the purchase order. */
    public Long getCreatedBy() {
        return this.createdBy;
    }

    /* Get the time stamp of when the purchase order was created. */
    public Timestamp getCreated() {
        return this.created;
    }

    /* Get the time stamp of when the purchase order was last modified. */
    public Timestamp getLastModified() {
        return this.lastModified;
    }

    /* Get the identifier for the person who last modified the purchase order. */
    public Long getLastModifiedById() {
        return this.lastModifiedById;
    }

    /** Get the set of products for the quote */
    public Set<ProductPurchaseOrder> getProductPurchaseOrder() {
        return productPurchaseOrder;
    }

    /* Set the identifier for the purchase order. */
    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    /* Set the identifier for the contact on the purchase order. */
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    /* Set the account on the purchase order. */
    public void setAccount(Account account) {
        this.account = account;
    }

    /* Set the owner of the purchase order. */
    public void setOwner(CrmUser owner) { this.owner = owner; }

    /* Set the identifier for the creator of the purchase order. */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /* Set the time stamp of when the purchase order was created. */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /* Set the time stamp of when the purchase order was last modified. */
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    /* Set the identifier for the person who last modified the purchase order. */
    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    /** Set the set of products for the sales order */
    public void setProductPurchaseOrder(Set<ProductPurchaseOrder> productPurchaseOrder) {
        this.productPurchaseOrder = productPurchaseOrder;
    }
}