package com.cylinder.contacts.model;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import com.cylinder.shared.model.SimpleAudit;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.accounts.model.Account;
import javax.validation.Valid;

/**
* @author Ryan Piper
* The information for some contact.
*/
@Entity
@Table(name="details", schema="contact")
public class Contact extends SimpleAudit {

    /**
    * The identifier for a contact.
    *
    * @param contactId the new contact id value.
    * @return the id of the contact.
    */
    @Getter
    @Setter
    @Id
    @Column(name="contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    /**
    * The first name of the lead.
    *
    * @param firstName the new first name of the lead.
    * @return the first name of the lead.
    */
    @Getter
    @Setter
    @Column(name="first_name")
    @Pattern(regexp="([a-zA-Z]{1,250})", message="Please enter a valid first name.")
    private String firstName;

    /**
    * The last name of the lead.
    *
    * @param lastName the new last name of the lead.
    * @return the last name of the lead.
    */
    @Getter
    @Setter
    @Column(name="last_name")
    @NotNull(message="Please provide the last name for the lead.")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid last name.")
    private String lastName;

    /**
    * The title or the position the lead retains.
    *
    * @param title the new title for the lead.
    * @return the title or position of the lead.
    */
    @Getter
    @Setter
    @Column
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid title.")
    private String title;

    /**
    * The department the contact is in.
    *
    * @param department the new department for the contact.
    * @return the department of the contact.
    */
    @Getter
    @Setter
    @Column
    @Pattern(regexp="[a-zA-Z\\s0-9]{1,250}", message="Please provide a valid department.")
    private String department;

    /**
    * The account/buisness the contact is associated with.
    *
    * @param account the new account value.
    * @return the account the contact is associated with.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    /**
    * The phone number of the contact.
    *
    * @param phoneNumber the new phone number value.
    * @return the phone number for the lead.
    */
    @Getter
    @Setter
    @Column
    private String phone;

    /**
    * The alternative phone number of the contact.
    *
    * @param otherPhone the new phone number value.
    * @return the alternative phone number for the contact.
    */
    @Getter
    @Setter
    @Column(name="other_phone")
    private String otherPhone;


    /**
    * The mobile number of the contact.
    *
    * @param mobile the new mobile value.
    * @return the mobile number for the contact.
    */
    @Getter
    @Setter
    @Column
    private String mobile;

    /**
    * The fax number of the contact.
    *
    * @param fax the new fax value.
    * @return the fax number for the contact.
    */
    @Getter
    @Setter
    @Column
    private String fax;

    /**
    * The birthday of the contact.
    *
    * @param dateOfBirth the new date of birth value.
    * @return the date of birth.
    */
    @Getter
    @Setter
    @Column(name="date_of_birth")
    private Date dateOfBirth;

    /**
    * The assistant to the contact.
    *
    * @param assistant the new assistant value for the contact.
    * @return the contact's assistant.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="assistant_id", referencedColumnName="contact_id")
    private Contact assistant;

    /**
    * The contact's boss.
    *
    * @param reportsTo the new report to value for the contact.
    * @return the contact's boss.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="report_to_id", referencedColumnName="contact_id")
    private Contact reportsTo;

    /**
    * The mailing address information associated to the contact.
    *
    * @param mailingAddress the address value for the contact.
    * @return the address of the contact.
    */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="mailing_address_id", referencedColumnName="address_id")
    private Address mailingAddress;

    /**
    * Alternative address information associated to the contact.
    *
    * @param otherAddress the address value for the contact.
    * @return the address of the contact.
    */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="other_address_id", referencedColumnName="address_id")
    private Address otherAddress;

    /**
    * The primary email the lead wants to be contacted through.
    *
    * @param primaryEmail the new primary email value.
    * @return the primary email of the lead.
    */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="primary_email_id", referencedColumnName="email_id")
    private Email  primaryEmail;

    /**
    * A alternative email if the primary email address doesn't work.
    *
    * @param secondaryEmail the new secondary email value.
    * @return the secondary email for the lead.
    */
    @Getter
    @Setter
    @Valid
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="secondary_email_id", referencedColumnName="email_id")
    private Email  secondaryEmail;

    /**
    * The twitter handlebar the contact uses.
    *
    * @param twitter the new  twitter value.
    * @return the twitter handlebar associated to the contact.
    */
    @Getter
    @Setter
    @Column
    @Pattern(regexp="@.*", message="Please provide a valid twitter handler.")
    private String twitter;

    /**
    * The twitter handlebar the contact uses.
    *
    * @param twitter the new  twitter value.
    * @return the twitter handlebar associated to the contact.
    */
    @Getter
    @Setter
    @Column
    @Pattern(regexp=".{1,250}", message="Please provide a valid twitter handler.")
    private String skype;

    /**
    * Get the user that is responsible for this lead.
    *
    * @param owner the new owner of the lead.
    * @return the owner of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id", referencedColumnName = "account_id")
    private CrmUser owner;

    public Contact() {}

    /**
    * A shortcut method to get the first and last name of the lead.
    * @return the full name of the lead.
    */
    public String getFullName() {
      if (this.firstName == null) {
        return this.lastName;
      } else {
        return this.firstName + " " + this.lastName;
      }
    }
}
