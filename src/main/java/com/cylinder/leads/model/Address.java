package com.cylinder.leads.model;

import javax.persistence.*;
import com.cylinder.shared.*;

@Entity
@Table(name="addresses", schema="lead")
public class Address extends SimpleAddress {
  public Address(){}
}
