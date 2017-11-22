package com.cylinder.accounts.model;

import com.cylinder.contacts.model.Contact;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Ehtasham Munib
 */

@Entity
@Table(name = "details", schema = "account")
public class Account extends SimpleAudit {
    /**
     * The identifier for an account.
     *
     * @param accountId the new account id value.
     * @return the id of the account.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    /**
     * The name for an account.
     *
     * @param accountName the new name of the account.
     * @return the name of the account.
     */
    @Getter
    @Setter
    @Column(name = "name")
    @Pattern(regexp = "([a-zA-Z]{1,250})", message = "Please enter a valid account name.")
    @NotNull(message = "Please enter a name")
    private String accountName;

    /**
     * The rating for an account.
     *
     * @param rating the new account rating value.
     * @return the rating of the account.
     */
    @Getter
    @Setter
    @DecimalMax(value = "10.0", message = "Please enter a valid value between 0 and 10")
    @DecimalMin(value = "0.0", message = "Please enter a valid value between 0 and 10")
    @Column(name = "rating")
    private double rating;

    /**
     * The phone number for an account.
     *
     * @param phone the new account phone number value.
     * @return the phone number of the account.
     */
    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
    @Column(name = "phone")
    private String phone;

    /**
     * The secondary phone number for an account.
     *
     * @param otherPhone the new secondary phone number value.
     * @return the secondary phone number of the account.
     */
    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
    @Column(name = "other_phone")
    private String otherPhone;

    /**
     * The fax number for an account.
     *
     * @param fax the new account fax number value.
     * @return the fax number of the account.
     */
    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
    @Column(name = "fax")
    private String fax;

    /**
     * The parent account for an account.
     *
     * @param parent the new parent value.
     * @return the parent of the account.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "account_id")
    private Account parent;

    /**
     * The type for an account.
     *
     * @param type the new type value.
     * @return the type of the account.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    /**
     * The ticker symbol for an account.
     *
     * @param tickerSymbol the new ticker symbol value.
     * @return the ticker symbol of the account.
     */
    @Getter
    @Setter
    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    /**
     * The website for an account.
     *
     * @param website the new website value.
     * @return the website of the account.
     */
    @Getter
    @Setter
    @Column(name = "website")
    private String website;

    /**
     * The number of employees for an account.
     *
     * @param numberEmployees the new number of employees value.
     * @return the number of employees of the account.
     */
    @Getter
    @Setter
    @Column(name = "number_of_employees")
    @Min(value = 1, message = "There must be at least 1 employee")
    private int numberEmployees;

    /**
     * The billing address for an account.
     *
     * @param billingAddress the new billing address value.
     * @return the billing address of the account.
     */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "address_id")
    private Address billingAddress;

    /**
     * The shipping address for an account.
     *
     * @param shippingAddress the new shipping address value.
     * @return the shipping address of the account.
     */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    /**
     * The owner for an account.
     *
     * @param owner the new owner value.
     * @return the owner of the account.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id")
    private CrmUser owner;

    /**
     * The primary email for an account.
     *
     * @param primaryEmail the new primary email value.
     * @return the primary email of the account.
     */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_email_id", referencedColumnName = "email_id")
    private Email primaryEmail;

    /**
     * The secondary email for an account.
     *
     * @param secondaryEmail the new secondary email value.
     * @return the secondary email of the account.
     */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_email_id", referencedColumnName = "email_id")
    private Email secondaryEmail;

    /**
     * The contacts for an account.
     *
     * @param contacts the new contacts value.
     * @return the contacts of the account.
     */
    @Getter
    @Setter
    @OneToMany(mappedBy = "account")
    private Set<Contact> contacts = new HashSet<>();

//    /**
//     * The deals for an account.
//     *
//     * @param deals the new deals value.
//     * @return the deals of the account.
//     */
//    @Getter
//    @Setter
//    @OneToMany(mappedBy = "account")
//    private Set<Deal> deals = new HashSet<>();
//
//    /**
//     * The cases for an account.
//     *
//     * @param cases the new cases value.
//     * @return the cases of the account.
//     */
//    @Getter
//    @Setter
//    @OneToMany(mappedBy = "account")
//    private Set<Case> cases = new HashSet<>();

    public Account() {
    }
}