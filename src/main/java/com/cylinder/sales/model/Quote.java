package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "quotes", schema = "sale")
public class Quote {
    @Id
    @Column(name="quote_id")
    private Long quoteId;

    /** The identifyer of the contact associated with the quote. */
    @Column(name="contact_id")
    private Long contactId;

    /** The identifyer of the account associated with the quote. */
    @Column(name="account_id")
    private Long accountId;

    /** The identifyer of the owner of the quote. */
    @Column(name="owner_id")
    private Long ownerId;

    /** The identifyer of the creator of the quote. */
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the quote was created. */
    @Column(name="created")
    private Timestamp created;

    /** The time stamp of when the quote was last modified. */
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the quote. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    public Quote() { }

    /* Get the identifier for the quote. */
    public Long getQuoteId() {
        return quoteId;
    }

    /* Get the identifier for the contact on the quote. */
    public Long getContactId() {
        return contactId;
    }

    /* Get the identifier for the account on the quote. */
    public Long getAccountId() {
        return accountId;
    }

    /* Get the identifier for the owner of the quote. */
    public Long getOwnerId() {
        return ownerId;
    }

    /* Get the identifier for the creator of the quote. */
    public Long getCreatedBy() {
        return createdBy;
    }

    /* Get the time stamp of when the quote was created. */
    public Timestamp getCreated() {
        return created;
    }

    /* Get the time stamp of when the quote was last modified. */
    public Timestamp getLastModified() { return lastModified; }

    /* Get the identifier for the person who last modified the quote. */
    public Long getLastModifiedById() { return lastModifiedById; }

    /* Set the identifier for the quote. */
    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    /* Set the identifier for the contact on the quote. */
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    /* Set the identifier for the account on the quote. */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /* Set the identifier for the owner of the quote. */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /* Set the identifier for the creator of the quote. */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /* Set the time stamp of when the quote was created. */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /* Set the time stamp of when the quote was last modified. */
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    /* Set the identifier for the person who last modified the quote. */
    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }
}