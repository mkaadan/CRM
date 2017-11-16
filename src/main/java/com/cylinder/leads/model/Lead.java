package com.cylinder.leads.model;

import com.cylinder.global.Industry;
import com.cylinder.crmusers.model.CrmUser;

import java.util.List;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.*;

@Entity
@Table(name="details", schema="lead")
public class Lead {

    /**
    * The identifier for a lead.
    *
    * @param leadId the new lead id value.
    * @return the id of the lead.
    */
    @Getter
    @Setter
    @Id
    @Column(name="lead_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long leadId;

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
    protected String firstName;

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
    protected String lastName;

    /**
    * The title or the position the lead retains.
    *
    * @param title the new title for the lead.
    * @return the title or position of the lead.
    */
    @Getter
    @Setter
    @Column(name="title")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid title.")
    protected String title;

    /**
    * The name of the company the lead is claiming to be part of.
    *
    * @param companyName the new company name value.
    * @return the name of the company the lead is associated to.
    */
    @Getter
    @Setter
    @Column(name="company_name")
    @Pattern(regexp=".{1,250}")
    protected String companyName;

    /**
    * The phone number of the lead.
    *
    * @param phoneNumber the new phone number value.
    * @return the phone number for the lead.
    */
    @Getter
    @Setter
    @Column
    protected String phone;

    /**
    * The mobile number of the lead.
    *
    * @param mobile the new mobile value.
    * @return the mobile number for the lead.
    */
    @Getter
    @Setter
    @Column
    protected String mobile;

    /**
    * The status of the lead, that is, are we waiting to contact the lead? Have
    * we already contacted them?
    *
    * @param status the new lead status value.
    * @return the status of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    protected Status status;

    /**
    * Any address information associated to the lead.
    *
    * @param address the address value for the lead.
    * @return the address of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName="address_id")
    protected Address address;

    /**
    * What industry is the lead part of?
    *
    * @param industry the new industry value.
    * @return the industry the lead is part of.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    protected Industry industry;

    /**
    * The primary email the lead wants to be contacted through.
    *
    * @param primaryEmail the new primary email value.
    * @return the primary email of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="primary_email_id", referencedColumnName="email_id")
    protected Email  primaryEmail;

    /**
    * A alternative email if the primary email address doesn't work.
    *
    * @param secondaryEmail the new secondary email value.
    * @return the secondary email for the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="secondary_email_id", referencedColumnName="email_id")
    protected Email  secondaryEmail;

    /**
    * How did we in contact with this lead? Though a trade show? A cold call?
    *
    * @param source the new source value.
    * @return the source of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="source_id", referencedColumnName = "source_id")
    protected Source source;

    /**
    * The twitter handlebar the lead uses.
    *
    * @param twitter the new  twitter value.
    * @return the twitter handlebar associated to the lead.
    */
    @Getter
    @Setter
    @Column
    @Pattern(regexp="@.*", message="Please provide a valid twitter handler.")
    protected String twitter;

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
    protected CrmUser owner;

    /**
    * Get the user that last created this lead.
    *
    * @param createdBy the creator of the lead.
    * @return the creator of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="created_by", referencedColumnName = "account_id", updatable=false)
    protected CrmUser createdBy;

    /**
    * Get the user that last modified this lead.
    *
    * @param owner the new owner of the lead.
    * @return the owner of the lead.
    */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="last_modified_by_id", referencedColumnName = "account_id")
    protected CrmUser lastModifiedBy;

    /**
    * The time that this record was created.
    *
    * @return the timestamp of when this lead was added.
    */
    @Getter
    @CreationTimestamp
    @Column(updatable=false)
    protected Timestamp created;

    /**
    * The time that this record was last modified.
    *
    * @return the timestamp of when this lead was last modified.
    */
    @Getter
    @UpdateTimestamp
    @Column(name = "last_modified")
    protected Timestamp lastModified;

    public Lead() {}

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
