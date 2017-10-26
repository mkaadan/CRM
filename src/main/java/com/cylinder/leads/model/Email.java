package com.cylinder.leads;


import javax.persistence.*;
import com.cylinder.shared.SimpleEmail;

@Entity
@Table(name="emails", schema="lead")
public class Email extends SimpleEmail {
  public Email(){}
}
