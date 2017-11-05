package com.cylinder.accounts.model;

import com.cylinder.crmusers.model.CrmUser;

import javax.persistence.*;

@Entity
@Table(name = "details", schema = "account")
public class Account {
    @Id
    @Column(name = "account_id")
    private long accountId;

    @Column(name = "name")
    private String accountName;

    @Column(name = "rating")
    private double rating;

    @Column(name = "phone")
    private String phone;

    @Column(name = "other_phone")
    private String otherPhone;

    @Column(name = "fax")
    private String fax;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id", referencedColumnName="account_id")
    private Account parent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @Column(name = "website")
    private String website;

    @Column(name = "number_of_employees")
    private int numberEmployees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_address_id", referencedColumnName="address_id")
    private Address billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_address_id", referencedColumnName="address_id")
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    public Account() {

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

    public String getOwner() {
        return owner.getName();
    }

    public void setOwner(CrmUser owner) {
        this.owner = owner;
    }
}