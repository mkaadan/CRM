package com.cylinder.sales.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "contracts", schema = "sale")
public class Contract {
    @Id
    @Getter
    @Setter
    @Column(name="contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    /** The contract's title. */
    @Getter
    @Setter
    @Column(name="contract_title")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid title.")
    private String contractTitle;

    /** The contract's body text. */
    @Getter
    @Setter
    @Column(name="contract")
    @Pattern(regexp=".*", message="Please enter a proper body.")
    private String contractText;

    public Contract() { }

    /* Get the preview of the body text of the contract. */
    public String getContractPreviewText() {
        String contractPreviewText = "";
        if(contractText.length() > 100){
            contractPreviewText = contractText.substring(0,100) + "...";
            return contractPreviewText;
        }
        else{return this.contractText;}
    }
}
