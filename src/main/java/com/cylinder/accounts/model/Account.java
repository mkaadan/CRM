package com.cylinder.accounts.model;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "details", schema = "account")
public class Account extends SimpleAudit {
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

    @DecimalMax(value = "10.0", message = "Please enter a valid value between 0 and 10")
    @DecimalMin(value = "0.0", message = "Please enter a valid value between 0 and 10")
    @Column(name = "rating")
    private double rating;

    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
    @Column(name = "phone")
    private String phone;

    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
    @Column(name = "other_phone")
    private String otherPhone;

    @Getter
    @Setter
    @Pattern(regexp = "[0-9\\+\\-]{1,20}", message = "Please provide a valid phone number.")
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
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "address_id")
    private Address billingAddress;

    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id")
    private CrmUser owner;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="primary_email_id", referencedColumnName="email_id")
    private Email primaryEmail;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="secondary_email_id", referencedColumnName="email_id")
    private Email  secondaryEmail;

    public Account() {

    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_email_id", referencedColumnName = "email_id")
    private Email secondaryEmail;

    public Email getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(Email primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Email getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(Email secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getNumberEmployees() {
        return numberEmployees;
    }

    public void setNumberEmployees(int numberEmployees) {
        this.numberEmployees = numberEmployees;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CrmUser getOwner() {
        return owner;
    }

    public void setOwner(CrmUser owner) {
        this.owner = owner;
    }
}