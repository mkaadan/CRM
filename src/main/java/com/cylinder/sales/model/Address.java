package com.cylinder.sales.model;

import com.cylinder.shared.model.SimpleAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses", schema = "sale")
public class Address extends SimpleAddress {
    public Address() {
    }
}
