package com.cylinder.sales.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "contracts", schema = "sale")
public class Contract {
    @Id
    @Column(name="contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    /** The contract's title. */
    @Column(name="contract_title")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid title.")
    private String contractTitle;

    /** The contract's body text. */
    @Column(name="contract")
    private String contractText;

    public Contract() { }

    /* Get the identifier for the contract. */
    public long getContractId() {
        return this.contractId;
    }

    /* Get the title of the contract. */
    public String getContractTitle() { return this.contractTitle; }

    /* Get the body text of the contract. */
    public String getContractText() { return this.contractText; }

    /* Get the preview of the body text of the contract. */
    public String getContractPreviewText() {
        String contractPreviewText = "";
        if(contractText.length() > 100){
            contractPreviewText = contractText.substring(0,100) + "...";
            return contractPreviewText;
        }
        else{return this.contractText;}
    }

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
