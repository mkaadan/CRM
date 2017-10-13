package com.cylinder.leads;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="contacts", schema="lead")
class Lead {
    @Id
    @Column(name="contact_id")
    public Long leadId;
    /** The first name of the lead. */
    @Column(name="first_name")
    public String firstName;
    /** The last name of the lead. *

    /** Construct for JPA **/
    protected Lead() {}

    /**
    * Construct a new instance of a lead.
    * @param name the full name of the lead.
    * @param company the name of the company where the lead represents.
    * @param email the email of the lead.
    * @param leadSource where did the lead originate from? A Trade show? Cold Call?
    * @param leadOwner whose is responible for this lead?
    */
    public Lead(Long leadId,
                String firstName) {
        this.leadId = leadId;
        this.firstName = firstName;
    }


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
}
