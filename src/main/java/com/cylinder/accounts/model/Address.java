package com.cylinder.accounts.model;

import javax.persistence.*;
import com.cylinder.shared.*;

@Entity
@Table(name="addresses", schema="account")
public class Address extends SimpleAddress {
  public Address(){}
}
