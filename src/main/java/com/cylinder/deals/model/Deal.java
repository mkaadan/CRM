package com.cylinder.deals.model;

import com.cylinder.shared.model.SimpleAudit;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.accounts.model.Account;

import com.cylinder.contacts.model.Contact;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

import java.sql.Date;

/**
* @author Ryan Piper
* Manages a sale deal.
*/
@Entity
@Table(name="details", schema="deal")
public class Deal extends SimpleAudit {

  /**
  * The identifier for a deal's stage.
  *
  * @param stageId the new lead id value.
  * @return the id of the deal's stage.
  */
  @Getter
  @Setter
  @Id
  @Column(name="deal_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dealId;

  /**
  * The name of the deal.
  *
  * @param dealName the new name of the deal.
  * @return the name of the deal.
  */
  @Getter
  @Setter
  @NotNull
  @Pattern(regexp=".{1,50}")
  @Column(name="name")
  private String dealName;

  /**
  * The owner responsible for the deal.
  *
  * @param owner the new owner for the deal.
  * @return the owner of the deal.
  */
  @Getter
  @Setter
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="owner_id", referencedColumnName="account_id")
  private CrmUser owner;

  /**
  * The deal's type.
  *
  * @param type the new type value for the deal.
  * @return the deal's type.
  */
  @Getter
  @Setter
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="type_id")
  private Type type;

  /**
  * The stage the deal is in.
  *
  * @param stage the new stage for the deal.
  * @return the deal's stage.
  */
  @Getter
  @Setter
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="stage_id")
  private Stage stage;

  /**
  * The expected revenue to be made from the deal.
  *
  * @param expectedRevenue the new expected revenue for the deal.
  * @return the expected revenue for the deal.
  */
  @Getter
  @Setter
  @Column(name="expected_rev")
  private BigDecimal expectedRevenue;

  /**
  * The id of the contact.
  *
  */
  @Getter
  @Setter
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="contact_id")
  private Contact contact;

  /**
  * The account that is assoicated to the deal.
  *
  * @param account the new account assoicated to the deal.
  * @return the assoicated account to the deal.
  */
  @Getter
  @Setter
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="account_id")
  private Account account;

  /**
  * The actual amount of money that was earned as a result of the deal.
  *
  * @param actualAmountEarned the new actual earned amount from the deal.
  * @return the actual earned amount from the deal.
  */
  @Getter
  @Setter
  @Column(name="amount_earned")
  private BigDecimal actualAmountEarned;

  /**
  * The expected closing date of the deal.
  *
  * @param closingDate the new expected date to close the deal.
  * @return the expected date to close the deal.
  */
  @Getter
  @Setter
  @Column(name="closing_date")
  private Date closingDate;
}
