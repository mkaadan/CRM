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
    @Getter
    @Setter
    @Column(name="sales_order_id")
    private Long salesOrderId;

    /* Billing address information associated to the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_address_id", referencedColumnName="address_id")
    private Address billingAddress;

    /* Shipping address information associated to the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_address_id", referencedColumnName="address_id")
    private Address shippingAddress;

    /** The identifyer of the contact associated with the sales order. */
    @Getter
    @Setter
    @Column(name="contact_id")
    private Long contactId;

    /** The account associated with the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /** The quote associated with the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quote_id", referencedColumnName = "quote_id")
    private Quote quote;

    /** The tax ammount associated with the sales order. */
    @Getter
    @Setter
    @Column(name="tax_percent")
    private float taxPercent;

    /** The invoice number associated with the sales order. */
    @Getter
    @Setter
    @Column(name="invoice_number")
    private Long invoiceNumber;

    /** The contract associated with the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;

    /** The owner associated with the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName="account_id")
    private CrmUser owner;

    /** The identifyer of the creator of the sales order. */
    @Getter
    @Setter
    @Column(name="created_by")
    private Long createdBy;

    /** The time stamp of when the sales order was created. */
    @Getter
    @Setter
    @Column(name="created")
    private Timestamp created;

    /** The time stamp of when the sales order was last modified. */
    @Getter
    @Setter
    @Column(name="last_modified")
    private Timestamp lastModified;

    /** The identifyer of who last modified the sales order. */
    @Getter
    @Setter
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

    /** The set of products for the sales order. */
    @Getter
    @Setter
    @OneToMany(mappedBy = "salesOrder")
    private Set<ProductSalesOrder> productSalesOrder;

    public SalesOrder() { }
}
