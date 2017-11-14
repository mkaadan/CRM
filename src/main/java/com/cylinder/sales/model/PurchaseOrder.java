package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;

import javax.persistence.*;

@Entity
@Table(name = "purchase_orders", schema = "sale")
public class PurchaseOrder {
    @Id
    @Column(name="purchase_order_id")
    private Long purchaseOrderId;

    /** The identifyer of the contact associated with the purchase order. */
    @Column(name="contact_id")
    private Long contactId;

    /** The identifyer of the account associated with the purchase order. */
    @Column(name="account_id")
    private Long accountId;

    /** The identifyer of the owner of the purchase order. */
    @Column(name="owner_id")
    private Long ownerId;

    /** The identifyer of the creator of the purchase order. */
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the purchase order was created. */
    @Column(name="created")
    private Time created;

    /** The time stamp of when the purchase order was last modified. */
    @Column(name="last_modified")
    private Time lastModified;

    /** The identifyer of who last modified the purchase order. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    public PurchaseOrder() { }

    /* Get the identifier for the purchase order. */
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    /* Get the identifier for the contact on the purchase order. */
    public Long getContactId() {
        return contactId;
    }

    /* Get the identifier for the account on the purchase order. */
    public Long getAccountId() {
        return accountId;
    }

    /* Get the identifier for the owner of the purchase order. */
    public Long getOwnerId() {
        return ownerId;
    }

    /* Get the identifier for the creator of the purchase order. */
    public Long getCreatedBy() {
        return createdBy;
    }

    /* Get the time stamp of when the purchase order was created. */
    public Time getCreated() {
        return created;
    }

    /* Get the time stamp of when the purchase order was last modified. */
    public Time getLastModified() {
        return lastModified;
    }

    /* Get the identifier for the person who last modified the purchase order. */
    public Long getLastModifiedById() {
        return lastModifiedById;
    }

    /* Set the identifier for the purchase order. */
    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    /* Set the identifier for the contact on the purchase order. */
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    /* Set the identifier for the account on the purchase order. */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /* Set the identifier for the owner of the purchase order. */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /* Set the identifier for the creator of the purchase order. */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /* Set the time stamp of when the purchase order was created. */
    public void setCreated(Time created) {
        this.created = created;
    }

    /* Set the time stamp of when the purchase order was last modified. */
    public void setLastModified(Time lastModified) {
        this.lastModified = lastModified;
    }

    /* Set the identifier for the person who last modified the purchase order. */
    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }
}