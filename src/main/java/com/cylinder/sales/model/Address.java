package com.cylinder.sales.model;

import javax.persistence.*;
import com.cylinder.shared.model.SimpleAddress;

@Entity
@Table(name="addresses", schema="sale")
public class Address extends SimpleAddress {
  public Address(){}
}
