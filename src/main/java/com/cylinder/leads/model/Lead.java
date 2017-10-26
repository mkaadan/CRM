package com.cylinder.leads;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="details", schema="lead")
class Lead {
    @Id
    @Column(name="lead_id")
    protected Long leadId;
    /** The first name of the lead. */
    @Column(name="first_name")
    protected String firstName;

    @Column(name="last_name")
    protected String lastName;

    @Column(name="title")
    protected String title;

    @Column(name="company_name")
    protected String companyName;

    @Column(name="phone")
    protected String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    protected Status status;

    @Column(name="mobile")
    protected String mobile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName="address_id")
    protected Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    protected Industry industry;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable (
      name="email_lookups",
      schema="lead",
      joinColumns={ @JoinColumn(name="lead_id", referencedColumnName="lead_id") },
      inverseJoinColumns={ @JoinColumn(name="email_id", referencedColumnName="email_id") }
   )
    public List<Email> emails;


    /** Construct for JPA **/
    protected Lead() {}

  public Long getLeadId() {
      return this.leadId;
  }

  public String getFirstName() {
      return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  public String getTitle() {
    return this.title;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public Industry getIndustry() {
    return this.industry;
  }

  public List<Email> getEmails() {
    return this.emails;
  }

  public Address getAddress() {
    return this.address;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setLeadId(long id) {
      this.leadId = id;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public void setIndustry(Industry industry) {
    this.industry = industry;
  }

  public void setEmails(List<Email> emails) {
    this.emails = emails;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
