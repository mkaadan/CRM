package com.cylinder.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.validator.routines.EmailValidator;

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

  // is the given email rfc 822 compliant?
  public static boolean isValidEmail(String potentialEmail) {
    return EmailValidator.getInstance().isValid(potentialEmail);
  }
}
