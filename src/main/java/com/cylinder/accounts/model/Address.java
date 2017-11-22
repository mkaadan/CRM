package com.cylinder.accounts.model;

import com.cylinder.shared.model.SimpleAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ehtasham Munib
 * Maps account.address table to an object.
 */
@Entity
@Table(name = "addresses", schema = "account")
public class Address extends SimpleAddress {
    public Address() {
    }
}
