package com.cylinder.leads;

import javax.persistence.*;

@Entity
@Table(name="emails", schema="lead")
public class LeadEmail {

  @Id
  @Column(name="email_id")
  protected Long emailId;
  @Column(name="email")
  protected String email;

  public LeadEmail(){}
    
  public Long getEmailId() {
    return this.emailId;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmailId(Long id) {
     this.emailId = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return this.email;
  }


}
