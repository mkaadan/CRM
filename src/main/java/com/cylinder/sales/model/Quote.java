package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "quotes", schema = "sale")
public class Quote{
    /** The identifyer of the quote. */
    @Id
    @Column(name="quote_id")
    private Long quoteId;

    /** The identifyer of the contact associated with the quote. */
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the quote. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The owner associated with the quote. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

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

    /** The set of products for the quote. */
    @OneToMany(mappedBy = "quote")
    private Set<ProductQuote> productQuote;

    public Quote() { }

    /* Get the identifier for the quote. */
    public Long getQuoteId() { return this.quoteId; }

    /* Get the identifier for the contact on the quote. */
    public Long getContactId() { return this.contactId; }

    /* Get the account on the quote. */
    public Account getAccount() { return this.account; }

    /* Get the owner of the quote. */
    public CrmUser getOwner() { return this.owner; }

    /* Get the identifier for the creator of the quote. */
    public Long getCreatedBy() { return this.createdBy; }

    /* Get the time stamp of when the quote was created. */
    public Timestamp getCreated() { return this.created; }

    /* Get the time stamp of when the quote was last modified. */
    public Timestamp getLastModified() { return this.lastModified; }

    /* Get the identifier for the person who last modified the quote. */
    public Long getLastModifiedById() { return this.lastModifiedById; }

    /** Get the set of products for the quote */
    public Set<ProductQuote> getProductQuote() {
        return productQuote;
    }

    /* Set the identifier for the quote. */
    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    /* Set the identifier for the contact on the quote. */
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    /* Set the account on the quote. */
    public void setAccount(Account account) {
        this.account = account;
    }

    /* Set the owner of the quote. */
    public void setOwner(CrmUser owner) { this.owner = owner; }

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

    /** Set the set of products for the quote */
    public void setProductQuote(Set<ProductQuote> productQuote) {
        this.productQuote = productQuote;
    }
}