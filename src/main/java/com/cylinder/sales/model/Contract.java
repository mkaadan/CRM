package com.cylinder.sales.model;

import com.cylinder.crmusers.model.CrmUser;

import javax.persistence.*;

@Entity
@Table(name = "contracts", schema = "sale")
public class Contract {
    @Id
    @Column(name="contract_id")
    private Long contractId;

    /** The contrac's title. */
    @Column(name="contract_title")
    private String contractTitle;

    /** The contrac's body text. */
    @Column(name="contract")
    private String contractText;

    public Contract() { }

    /* Get the identifier for the contract. */
    public long getContractId() {
        return contractId;
    }

    /* Get the title of the contract. */
    public String getContractTitle() { return contractTitle; }

    /* Get the body text of the contract. */
    public String getContractText() { return contractText; }

    /* Set the identifier for the lead. */
    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    /* Set the title of the contract. */
    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    /* Set the body text of the contract. */
    public void setContractText(String contractText) {
        this.contractText = contractText;
    }
}