package com.cylinder.leads;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="contacts", schema="lead")
class Lead {
    @Id
    @Column(name="contact_id")
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

    @Column(name="phone_ext")
    protected String phone_ext;

    @Column(name="mobile")
    protected String mobile;

    @Column(name="fax")
    protected String fax;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName="address_id")
    protected LeadAddress address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    protected Industry industry;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable (
      name="email_lookups",
      schema="lead",
      joinColumns={ @JoinColumn(name="contact_lookup_id", referencedColumnName="contact_id") },
      inverseJoinColumns={ @JoinColumn(name="email_lookup_id", referencedColumnName="email_id") }
   )
    public List<LeadEmail> emails;


    /** Construct for JPA **/
    protected Lead() {}

  public Long getLeadId() {
      return this.leadId;
  }

  public String getFirstName() {
      return this.firstName;
  }

  public String getTitle() {
    return this.title;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getFax() {
    return this.fax; 
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public Industry getIndustry() {
    return this.industry;
  }

  public List<LeadEmail> getEmails() {
    return this.emails;
  }

  public LeadAddress getAddress() {
    return this.address;
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

  public void setEmails(List<LeadEmail> emails) {
    this.emails = emails;
  }

  public void setAddress(LeadAddress address) {
    this.address = address;
  }

  @Override
  public String toString() {
      String email_string = "";
      for (LeadEmail item: this.emails) {
        email_string = email_string + item.toString();
      }
      return String.format(
              "Lead[leadId=%d, firstName='%s', industryName='%s', emails='%s']",
              leadId, firstName, this.industry.toString(), email_string);
  }
}
