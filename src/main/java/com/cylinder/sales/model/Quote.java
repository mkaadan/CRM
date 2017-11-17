package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "quotes", schema = "sale")
public class Quote extends SimpleAudit{
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

    /** The set of products for the quote. */
    @Getter
    @Setter
    @OneToMany(mappedBy = "quote")
    private Set<ProductQuote> productQuote;

    public Quote() { }
}