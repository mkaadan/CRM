package com.cylinder.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class SimpleEmail {
  @Id
  @Column(name="email_id")
  protected Long emailId;
  @Column(name="email")
  protected String email;

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
}
