package com.cylinder.accounts.model;

import com.cylinder.crmusers.model.CrmUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "details", schema = "account")
public class Account {
    @Getter
    @Setter
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Getter
    @Setter
    @Column(name = "name")
    @Pattern(regexp="([a-zA-Z]{1,250})", message="Please enter a valid account name.")
    @NotNull(message = "Please enter a name")
    private String accountName;

    @Getter
    @Setter
    @DecimalMax(value = "10.0", message = "Please enter a valid value between 0 and 10")
    @DecimalMin(value = "0.0", message = "Please enter a valid value between 0 and 10")
    @Column(name = "rating")
    private double rating;

    @Getter
    @Setter
    @Column(name = "phone")
    private String phone;

    @Getter
    @Setter
    @Column(name = "other_phone")
    private String otherPhone;

    @Getter
    @Setter
    @Column(name = "fax")
    private String fax;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "account_id")
    private Account parent;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    @Getter
    @Setter
    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @Getter
    @Setter
    @Column(name = "website")
    private String website;

    @Getter
    @Setter
    @Column(name = "number_of_employees")
    @Min(value = 1, message = "There must be at least 1 employee")
    private int numberEmployees;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "address_id")
    private Address billingAddress;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id")
    private CrmUser owner;

    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="primary_email_id", referencedColumnName="email_id")
    private Email primaryEmail;

    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="secondary_email_id", referencedColumnName="email_id")
    private Email  secondaryEmail;

    public Account() {}
}