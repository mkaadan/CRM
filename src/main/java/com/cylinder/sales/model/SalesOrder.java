package com.cylinder.sales.model;

import com.cylinder.accounts.model.Account;
import com.cylinder.contacts.model.Contact;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.shared.model.SimpleAudit;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "sales_orders", schema = "sale")
public class SalesOrder extends SimpleAudit{
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sales_order_id")
    private Long salesOrderId;

    /* Billing address information associated to the sales order. */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_address_id", referencedColumnName="address_id")
    private Address billingAddress;

    /* Shipping address information associated to the sales order. */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_address_id", referencedColumnName="address_id")
    private Address shippingAddress;

    /** The identifyer of the contact associated with the sales order. */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    private Contact contact;

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
    @DecimalMax(value = "0.99", message = "Please enter a valid tax percent.")
    @DecimalMin(value = "0.00", message = "Please enter a valid tax percent.")
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

    /** The set of products for the sales order. */
    @Getter
    @Setter
    @OneToMany(mappedBy = "salesOrder")
    private List<ProductSalesOrder> productSalesOrder = new ArrayList<ProductSalesOrder>();

    /**
     * Calculate a subtotal for the sale
     * @return the subtotal of the sales
     */
    public BigDecimal getSubTotal(){
        BigDecimal subTotal = BigDecimal.ZERO;
        for(ProductSalesOrder productEntry: productSalesOrder) {
            if (productEntry.getProduct() != null) {
                subTotal = subTotal.add(productEntry.getUnitTotal());
            }
        }
        return subTotal;
    }

    /**
     * Calculate the total taxes for the sale
     * @return the total of the sale's taxes
     */
    public BigDecimal getTotalTaxes(){
        BigDecimal totalTaxes = getSubTotal().multiply(new BigDecimal(taxPercent));
        totalTaxes = totalTaxes.setScale(2,RoundingMode.CEILING);
        return totalTaxes;
    }

    /**
     * Calculate the total for the sale
     * @return the total of the sale
     */
    public BigDecimal getSaleTotal(){
        BigDecimal saleTotal = getSubTotal().add(getTotalTaxes());
        return saleTotal;
    }

    public SalesOrder() { }

}