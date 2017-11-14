package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
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
    private Timestamp created;

    /** The time stamp of when the purchase order was last modified. */
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the purchase order. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    public PurchaseOrder() { }

    /* Get the identifier for the purchase order. */
    public Long getPurchaseOrderId() {
        return this.purchaseOrderId;
    }

    /* Get the identifier for the contact on the purchase order. */
    public Long getContactId() {
        return this.contactId;
    }

    /* Get the identifier for the account on the purchase order. */
    public Long getAccountId() {
        return this.accountId;
    }

    /* Get the identifier for the owner of the purchase order. */
    public Long getOwnerId() {
        return this.ownerId;
    }

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
}