package com.cylinder.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class SimpleAddress {
  @Id
  @Column(name="address_id")
  protected Long addressId;
  @Column(name="apartment_number")
  protected String apartmentNumber;
  @Column(name="city")
  protected String city;
  @Column(name="street")
  protected String streetAddress;
  @Column(name="prov_state")
  protected String stateProv;
  @Column(name="country")
  protected String country;
  @Column(name="zip_postal")
  protected String zipPostal;
  @Column(name="po_box")
  protected String poBox;

  public Long getAddressId() {
    return this.addressId;
  }

  public String getApartmentNumber() {
    return this.apartmentNumber;
  }

  public String getCity() {
    return this.city;
  }

  public String getStreetAddress() {
    return this.streetAddress;
  }

  public String getStateProv() {
    return this.stateProv;
  }

  public String getPoBox() {
    return this.poBox;
  }

  public String getZipPostal() {
    return this.zipPostal;
  }

  public String getCountry() {
    return this.country;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setStateProv(String stateProv) {
    this.stateProv = stateProv;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }

  public void setPoBox(String poBox) {
    this.poBox = poBox;
  }

  public void setZipPostal(String zipPostal) {
    this.zipPostal = zipPostal;
  }

}
