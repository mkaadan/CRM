package com.cylinder.accounts.model;


import com.cylinder.shared.SimpleEmail;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="emails", schema="account")
public class Email extends SimpleEmail {
  public Email(){}
}
