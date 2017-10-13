package com.cylinder.leads;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="contacts", schema="lead")
class Lead {
    @Id
    @Column(name="contact_id")
    public Long leadId;
    /** The first name of the lead. */
    @Column(name="first_name")
    public String firstName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "industry_id")
    public Industry industry;

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

  public void setLeadId(long id) {
      this.leadId = id;
  }

  public String getFirstName() {
      return this.firstName;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public Industry getIndustry() {
    return this.industry;
  }

  public void setIndustry(Industry industry) {
    return this.industry = industry;
  }

  public List<LeadEmail> getIndustry() {
    return this.emails;
  }

  public void setIndustry(Industry industry) {
    this.industry = industry;
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
