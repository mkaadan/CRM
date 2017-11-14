package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;

import javax.persistence.*;

@Entity
@Table(name = "sales_orders", schema = "sale")
public class PurchaseOrder {
    @Id
    @Column(name="sales_order")
    private Long salesOrderId;

    /* Billing address information associated to the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_address_id", referencedColumnName="billing_address_id")
    private Address billingAddress;

    /* Shipping address information associated to the sales order. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_address_id", referencedColumnName="shipping_address_id")
    private Address shippingAddress;

    /** The identifyer of the contact associated with the sales order. */
    @Column(name="contact_id")
    private Long contactId;

    /** The identifyer of the account associated with the sales order. */
    @Column(name="account_id")
    private Long accountId;

    /** The identifyer of the quote associated with the sales order. */
    @Column(name="quote_id")
    private Long quoteId;

    /** The tax percent to be charged on the sales order. */
    @Column(name="tax_percent")
    private float taxPercent;

    /** The invoice number associated with the sales order. */
    @Column(name="invoice_number")
    private Long invoiceNumber;

    /** The identifyer of the contract associated with the sales order. */
    @Column(name="contract_id")
    private Long contractId;

    /** The identifyer of the owner of the sales order. */
    @Column(name="owner_id")
    private Long ownerId;

    /** The identifyer of the creator of the sales order. */
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the sales order was created. */
    @Column(name="created")
    private Time created;

    /** The time stamp of when the sales order was last modified. */
    @Column(name="last_modified")
    private Time lastModified;

    /** The identifyer of who last modified the sales order. */
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    public SalesOrder() { }

    /* Get the identifier for the sales order. */
    public Long getSalesOrderId() { return salesOrderId; }

    /* Get the identifier for the quote on the sales order. */
    public Long getQuoteId() { return quoteId; }

    /* Get the tax percent ammount for the sales order. */
    public float getTaxPercent() { return taxPercent; }

    /* Get the the invoice number on the sales order. */
    public Long getInvoiceNumber() { return invoiceNumber; }

    /* Get the identifier for the contract on the sales order. */
    public Long getContractId() { return contractId; }

    /* Get the identifier for the owner of the sales order. */
    public Long getOwnerId() { return ownerId; }

    /* Get the identifier for the creator of the sales order. */
    public Long getCreatedBy() {
        return createdBy;
    }

    /* Get the time stamp of when the sales order was created. */
    public Time getCreated() {
        return created;
    }

    /* Get the time stamp of when the sales order was last modified. */
    public Time getLastModified() { return lastModified; }

    /* Get the identifier for the person who last modified the sales order. */
    public Long getLastModifiedById() { return lastModifiedById; }

    /* Get the billing address information the sales order is associated with. */
    public Address getBillingAddress() { return billingAddress; }

    /* Get the shipping address information the sales order is associated with. */
    public Address getShippingAddress() { return shippingAddress; }

    /* Get the identifier for the contact on the sales order. */
    public Long getContactId() { return contactId; }

    /* Get the identifier for the account on the sales order. */
    public Long getAccountId() { return accountId; }

    /* Set the identifier for the sales order. */
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    /* Set the identifier for quote on the sales order. */
    public void setQuoteId(Long quoteId) { this.quoteId = quoteId; }

    /* Set the tax percent ammount for the sales order. */
    public void setTaxPercent(float taxPercent) { this.taxPercent = taxPercent; }

    /* Set the the invoice number on the sales order. */
    public void setInvoiceNumber(Long invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    /* Set the identifier for the contract on the sales order. */
    public void setContractId(Long contractId) { this.contractId = contractId; }

    /* Set the identifier for the owner of the sales order. */
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    /* Set the identifier for the creator of the sales order. */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /* Set the time stamp of when the sales order was created. */
    public void setCreated(Time created) {
        this.created = created;
    }

    /* Set the time stamp of when the sales order was last modified. */
    public void setLastModified(Time lastModified) {
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

    /* Set the identifier for the account on the sales order. */
    public void setAccountId(Long accountId) { this.accountId = accountId; }
}