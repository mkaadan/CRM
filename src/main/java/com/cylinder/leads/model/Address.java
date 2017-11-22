package com.cylinder.leads.model;

import com.cylinder.shared.model.SimpleAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ryan Piper
 * Maps lead.address table to an object.
 */
@Entity
@Table(name = "addresses", schema = "lead")
public class Address extends SimpleAddress {
    public Address() {
    }
}
