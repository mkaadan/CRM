package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "quotes", schema = "sale")
public class Quote{
    /** The identifyer of the quote. */
    @Id
    @Getter
    @Setter
    @Column(name="quote_id")
    private Long quoteId;

    /** The identifyer of the contact associated with the quote. */
    @Getter
    @Setter
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the quote. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The owner associated with the quote. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    /** The identifyer of the creator of the quote. */
    @Getter
    @Setter
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the quote was created. */
    @Getter
    @Setter
    @Column(name="created")
    private Timestamp created;

    /** The time stamp of when the quote was last modified. */
    @Getter
    @Setter
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the quote. */
    @Getter
    @Setter
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    /** The set of products for the quote. */
    @Getter
    @Setter
    @OneToMany(mappedBy = "quote")
    private Set<ProductQuote> productQuote;

    public Quote() { }
}