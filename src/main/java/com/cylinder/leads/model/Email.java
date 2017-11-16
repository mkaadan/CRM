package com.cylinder.leads.model;


import javax.persistence.*;
import com.cylinder.shared.model.SimpleEmail;

/**
* @author Ryan Piper
* The table mapping for a lead's email .
*/
@Entity
@Table(name="emails", schema="lead")
public class Email extends SimpleEmail {
  public Email(){}
}
