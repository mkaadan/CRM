package com.cylinder.leads.model;

import com.cylinder.global.Industry;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="details", schema="lead")
public class Lead {
    @Id
    @Column(name="lead_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long leadId;
    /** The first name of the lead. */
    @Column(name="first_name")
    @Pattern(regexp="([a-zA-Z])", message="Please enter a valid first name.")

    protected String firstName;

    @Column(name="last_name")
    @NotNull(message="Please provide the last name for the lead.")
    @Pattern(regexp="[a-zA-Z]", message="Please provide a valid last name.")
    protected String lastName;

    @Column(name="title")
    @Pattern(regexp="[a-zA-Z]", message="Please provide a valid title.")
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
    protected List<Email> emails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="source_id", referencedColumnName = "source_id")
    protected Source source;

    @Column
    @Pattern(regexp="@.*", message="Please provide a valid twitter handler.")
    protected String twitter;

    public Lead() {}

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

  public Status getStatus() {
    return this.status;
  }

  public Source getSource() {
    return this.source;
  }

  public String getTwitter() {
    return this.twitter;
  }

  public void setLeadId(long id) {
      this.leadId = id;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setCompanyName(String compName) {
    this.companyName = compName;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setstatus(Status status) {
    this.status = status;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public void setmobile(String mobile) {
    this.mobile = mobile;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }
}
