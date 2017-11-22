package com.cylinder.accounts.model;

import javax.persistence.*;
import com.cylinder.shared.model.SimpleAddress;

/**
 * @author Ehtasham Munib
 * Maps account.address table to an object.
 */
@Entity
@Table(name="addresses", schema="account")
public class Address extends SimpleAddress {
  public Address(){}
}
