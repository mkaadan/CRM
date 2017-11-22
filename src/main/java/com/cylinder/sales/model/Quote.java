package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.contacts.model.Contact;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quotes", schema = "sale")
public class Quote extends SimpleAudit {
    /**
     * The identifyer of the quote.
     */
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    private Long quoteId;

    /**
     * The identifyer of the contact associated with the quote.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    private Contact contact;

    /**
     * The account associated with the quote.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /**
     * The owner associated with the quote.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id")
    private CrmUser owner;

    /**
     * The set of products for the quote.
     */
    @Getter
    @Setter
    @OneToMany(mappedBy = "quote")
    private List<ProductQuote> productQuote = new ArrayList<ProductQuote>();

    public Quote() {
    }
}
