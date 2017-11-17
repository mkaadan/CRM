package com.cylinder.leads.model;

import javax.persistence.*;
import com.cylinder.shared.model.SimpleAddress;

/**
* @author Ryan Piper
* Maps lead.address table to an object.
*/
@Entity
@Table(name="addresses", schema="lead")
public class Address extends SimpleAddress {
  public Address(){}
}
