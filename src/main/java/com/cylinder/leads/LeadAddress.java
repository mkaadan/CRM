package com.cylinder.leads;

import javax.persistence.*;

@Entity
@Table(name="addresses", schema="lead")
public class LeadAddress {

  @Id
  @Column(name="address_id")
  protected Long addressId;
  @Column(name="city")
  protected String city;

  @Column(name="state_prov")
  protected String stateProv;

  @Column(name="apartment_number")
  protected String apartmentNumber;

  @Column(name="po_box")
  protected String poBox;

  @Column(name="zip_postal")
  protected String zipPostal;

  public LeadAddress(){}

  public Long getAddressId() {
    return this.addressId;
  }
  public String getCity() {
    return this.city;
  }
  public String getStateProv() {
    return this.stateProv;
  }
  public String getApartmentNumber() {
    return this.apartmentNumber;
  }
  public String getPoBox() {
    return this.poBox;
  }
  public String getZipPostal() {
    return this.zipPostal;
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
