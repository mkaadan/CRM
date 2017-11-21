package com.cylinder.contacts.model;

import javax.persistence.*;
import com.cylinder.shared.model.SimpleAddress;

/**
* @author Ryan Piper
* The contact's address information.
*/
@Entity
@Table(name="addresses", schema="contact")
public class Address extends SimpleAddress {
    public Address(){}
}
