package com.cylinder.leads.model;

import com.cylinder.global.Industry;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="details", schema="lead")
public class Lead {
    @Id
    @Column(name="lead_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long leadId;
    /** The first name of the lead. */
    @Column(name="first_name")
    @Pattern(regexp="([a-zA-Z]{1,250})", message="Please enter a valid first name.")
    protected String firstName;

    /** The last name of the lead. */
    @Column(name="last_name")
    @NotNull(message="Please provide the last name for the lead.")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid last name.")
    protected String lastName;

    /** The title or the position the lead retains. */
    @Column(name="title")
    @Pattern(regexp="[a-zA-Z]{1,250}", message="Please provide a valid title.")
    protected String title;

    /** The name of the company the lead is claiming to be part of. */
    @Column(name="company_name")
    @Pattern(regexp=".+{1,250}")
    protected String companyName;

    /** The phone number of the lead. */
    @Column(name="phone")
    protected String phone;

    /**
     The status of the lead, that is, are we waiting to contact the lead? Have
       we already contacted them? */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    protected Status status;

    /** The mobile number of the lead. */
    @Column(name="mobile")
    protected String mobile;

    /* Any address information associated to the lead. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName="address_id")
    protected Address address;

    /** What industry is the lead part of? */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    protected Industry industry;

    /** The primary email the lead wants to be contacted through. */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="primary_email_id", referencedColumnName="email_id")
    protected Email  primaryEmail;

    /** A alternative email if the primary email address doesn't work. */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="secondary_email_id", referencedColumnName="email_id")
    protected Email  secondaryEmail;

    /** How did we in contact with this lead? Though a trade show? A cold call? */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="source_id", referencedColumnName = "source_id")
    protected Source source;

    /** The twitter handlebar the lead uses. */
    @Column
    @Pattern(regexp="@.*", message="Please provide a valid twitter handler.")
    protected String twitter;

    public Lead() {}

    /* Get the identifier for the lead. */
    public Long getLeadId() {
        return this.leadId;
    }

    /* Get the first name of the lead. */
    public String getFirstName() {
        return this.firstName;
    }

    /* Get the las name of the lead. */
    public String getLastName() {
      return this.lastName;
    }

    /* A shortcut method to get the first and last name of the lead. */
    public String getFullName() {
      if (this.firstName == null) {
        return this.lastName;
      } else {
        return this.firstName + " " + this.lastName;
      }
    }

    /* Get the title associated to the lead. */
    public String getTitle() {
      return this.title;
    }

    /* Get the phone number associated to the lead. */
    public String getPhone() {
      return this.phone;
    }

    /* Get the company name associated to the lead. */
    public String getCompanyName() {
      return this.companyName;
    }

    /* Get the industry the lead associated with. */
    public Industry getIndustry() {
      return this.industry;
    }

    /* Get the primary email the lead is associated with. */
    public Email getPrimaryEmail() {
      return this.primaryEmail;
    }

    /* Get the secondary email the lead is asssociated with. */
    public Email getSecondaryEmail() {
      return this.secondaryEmail;
    }

    /* Get the address information the lead is associated with. */
    public Address getAddress() {
      return this.address;
    }

    /* Get the mobile number for the lead. */
    public String getMobile() {
      return this.mobile;
    }

    /* Get the status of the lead. */
    public Status getStatus() {
      return this.status;
    }

    /* Get the source for the lead. */
    public Source getSource() {
      return this.source;
    }

    /* Get the twitter handlebar for the lead. */
    public String getTwitter() {
      return this.twitter;
    }

    /* Set the id of the lead. */
    public void setLeadId(long id) {
        this.leadId = id;
    }

    /* Set the first name of the lead. */
    public void setFirstName(String name) {
      this.firstName = name;
    }

    /* Set the last name of the lead. */
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    /* Set the company name of the lead. */
    public void setCompanyName(String compName) {
      this.companyName = compName;
    }

    /* Set the phone number of the lead. */
    public void setPhone(String phone) {
      this.phone = phone;
    }

    /* Set the status of the lead. */
    public void setstatus(Status status) {
      this.status = status;
    }

    /* Set the source of the lead. */
    public void setSource(Source source) {
      this.source = source;
    }

    /* Set the mobile number for lead. */
    public void setmobile(String mobile) {
      this.mobile = mobile;
    }

    /* Set the title of the lead. */
    public void setTitle(String title) {
      this.title = title;
    }

    /* Set the industry for the lead. */
    public void setIndustry(Industry industry) {
      this.industry = industry;
    }

    /* Set the primary email for the lead. */
    public void setPrimaryEmail(Email email) {
      this.primaryEmail = email;
    }

    /* Set the secondary email for the lead. */
    public void setSecondaryEmail(Email email) {
      this.secondaryEmail = email;
    }

    /* Set the address information for the lead. */
    public void setAddress(Address address) {
      this.address = address;
    }

    /* Set the twitter handlebar for the lead. */
    public void setTwitter(String twitter) {
      this.twitter = twitter;
    }
}
