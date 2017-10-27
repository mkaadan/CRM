package com.cylinder.leads;

import javax.persistence.*;
import com.cylinder.shared.*;

@Entity
@Table(name="addresses", schema="lead")
public class Address extends SimpleAddress {
  public Address(){}
}
