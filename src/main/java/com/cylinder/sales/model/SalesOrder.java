package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.crmusers.model.CrmUser;
import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sales_orders", schema = "sale")
public class SalesOrder {
    @Id
    @Column(name="sales_order_id")
    private Long salesOrderId;

    /* Billing address information associated to the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_address_id", referencedColumnName="address_id")
    private Address billingAddress;

    /* Shipping address information associated to the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_address_id", referencedColumnName="address_id")
    private Address shippingAddress;

    /** The identifyer of the contact associated with the sales order. */
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The quote associated with the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quote_id", referencedColumnName = "quote_id")
    private Quote quote;

    /** The tax ammount associated with the sales order. */
    @Column(name="tax_percent")
    private float taxPercent;

    /** The invoice number associated with the sales order. */
    @Column(name="invoice_number")
    private Long invoiceNumber;

    /** The contract associated with the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;

    /** The owner associated with the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    /** The identifyer of the creator of the sales order. */
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the sales order was created. */
    @Column(name="created")
    private Timestamp created;

    /** The time stamp of when the sales order was last modified. */
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the sales order. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    /** The set of products for the sales order. */
    @OneToMany(mappedBy = "salesOrder")
    private Set<ProductSalesOrder> productSalesOrder;

    public SalesOrder() { }

    /* Get the identifier for the sales order. */
    public Long getSalesOrderId() { return this.salesOrderId; }

    /* Get for the quote on the sales order. */
    public Quote getQuote() { return this.quote; }

    /* Get the tax percent ammount for the sales order. */
    public float getTaxPercent() { return this.taxPercent; }

    /* Get the the invoice number on the sales order. */
    public Long getInvoiceNumber() { return this.invoiceNumber; }

    /* Get the contract on the sales order. */
    public Contract getContract() { return this.contract; }

    /* Get the owner of the sales order. */
    public CrmUser getOwner() { return this.owner; }

    /* Get the identifier for the creator of the sales order. */
    public Long getCreatedBy() {
        return this.createdBy;
    }

    /* Get the time stamp of when the sales order was created. */
    public Timestamp getCreated() {
        return this.created;
    }

    /* Get the time stamp of when the sales order was last modified. */
    public Timestamp getLastModified() { return this.lastModified; }

    /* Get the identifier for the person who last modified the sales order. */
    public Long getLastModifiedById() { return this.lastModifiedById; }

    /* Get the billing address information the sales order is associated with. */
    public Address getBillingAddress() { return this.billingAddress; }

    /* Get the shipping address information the sales order is associated with. */
    public Address getShippingAddress() { return this.shippingAddress; }

    /* Get the identifier for the contact on the sales order. */
    public Long getContactId() { return this.contactId; }

    /* Get the account on the sales order. */
    public Account getAccount() { return this.account; }

    /** Get the set of products for the quote */
    public Set<ProductSalesOrder> getProductSalesOrder() {
        return productSalesOrder;
    }

    /* Set the identifier for the sales order. */
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    /* Set the quote on the sales order. */
    public void setQuote(Quote quote) { this.quote = quote; }

    /* Set the tax percent ammount for the sales order. */
    public void setTaxPercent(float taxPercent) { this.taxPercent = taxPercent; }

    /* Set the the invoice number on the sales order. */
    public void setInvoiceNumber(Long invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    /* Set the contract on the sales order. */
    public void setContract(Contract contract) { this.contract = contract; }

    /* Set the owner of the sales order. */
    public void setOwner(CrmUser owner) { this.owner = owner; }

    /* Set the identifier for the creator of the sales order. */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /* Set the time stamp of when the sales order was created. */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /* Set the time stamp of when the sales order was last modified. */
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    /* Set the identifier for the person who last modified the sales order. */
    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    /* Set the billing address information the sales order is associated with. */
    public void setBillingAddress(Address billingAddress) { this.billingAddress = billingAddress; }

    /* Set the shipping address information the sales order is associated with. */
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }

    /* Set the identifier for the contact on the sales order. */
    public void setContactId(Long contactId) { this.contactId = contactId; }

    /* Set the account on the sales order. */
    public void setAccount(Account account) { this.account = account; }
}
