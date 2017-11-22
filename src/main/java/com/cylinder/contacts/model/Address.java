package com.cylinder.contacts.model;

import com.cylinder.shared.model.SimpleAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ryan Piper
 * The contact's address information.
 */
@Entity
@Table(name = "addresses", schema = "contact")
public class Address extends SimpleAddress {
    public Address() {
    }
}
