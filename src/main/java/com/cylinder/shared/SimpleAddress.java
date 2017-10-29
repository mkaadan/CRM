package com.cylinder.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Optional;


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

  public static boolean isValidAddress(String apartmentNumber,
                                       String city,
                                       String streetAddress,
                                       String stateProv,
                                       String country,
                                       String zipPostal,
                                       String poBox) {
      // at least one of values must be set other wise it isn't valid.
      if (apartmentNumber == null &&
          city == null &&
          streetAddress == null &&
          stateProv == null &&
          country == null &&
          zipPostal == null &&
          poBox == null) {
            return false;
      } else {
        return isValidApartmentNumber(apartmentNumber) &&
               isValidCity(city) &&
               isValidStreetAddress(streetAddress) &&
               isValidStateProv(stateProv) &&
               isValidCountry(country) &&
               isValidZipPostal(zipPostal) &&
               isValidPoBox(poBox);
      }
  }

  // The only consistent validation is number and letters. Austrialia has
  // 32/4 Street Address where germany it only the street number. So there isn't
  // a consistent validation for this.
  private static boolean isValidApartmentNumber(String apartmentNumber) {
    if (apartmentNumber != null) {
      Pattern apartmentNumberPattern = Pattern.compile("[0-9]+[a-zA-Z]");
      Matcher apartmentMatcher = apartmentNumberPattern.matcher(apartmentNumber);
      return apartmentMatcher.matches() && (apartmentNumber.length() <= 50);
    } else {
      return true;
    }
  }

  private static boolean isValidCity(String city) {
    if(city != null) {
      Pattern cityPattern = Pattern.compile("^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$");
      Matcher apartmentMatcher =  cityPattern.matcher(city);
      return apartmentMatcher.matches() && (city.length() <= 250);
    } else {
      return true;
    }
  }

  private static boolean isValidStreetAddress(String streetAddress) {
    if(streetAddress != null) {
      Pattern streetAddressPattern = Pattern.compile("^\\d+(?:\\s[A-Z]?[a-z]+)+");
      Matcher streetAddressMatcher =  streetAddressPattern.matcher(streetAddress);
      return streetAddressMatcher.matches() && (streetAddress.length() <= 250);
    } else {
      return true;
    }
  }

  private static boolean isValidStateProv(String stateProv) {
    if(stateProv != null) {
      Pattern stateProvPattern = Pattern.compile("^[A-Z][a-z]+$");
      Matcher stateMatcher = stateProvPattern.matcher(stateProv);
      return stateMatcher.matches() && (stateProv.length() <= 250);
    } else {
      return true;
    }
  }

  // Long term goal might be add geo database that meets ISO standards.
  private static boolean isValidCountry(String country) {
    if(country != null) {
      Pattern countryPattern = Pattern.compile("^[A-Z][a-z]+$");
      Matcher countryMatcher = countryPattern.matcher(country);
      return countryMatcher.matches() && (country.length() <= 250);
    } else {
      return true;
    }
  }

  // Zip postal code is country dependent so to certain degree all we can do is
  // ensure that it doesn't contain any special character. It has to up to the
  // the user to ensure the validity.
  private static boolean isValidZipPostal(String zipPostal) {
    if(zipPostal != null) {
      Pattern zipPostalPattern = Pattern.compile("^[a-zA-Z0-9]$");
      Matcher zipPostalMatcher = zipPostalPattern.matcher(zipPostal);
      return zipPostalMatcher.matches() && (zipPostal.length() <= 25);
    } else {
      return true;
    }
  }

  private static boolean isValidPoBox(String poBox) {
    if(poBox != null) {
      // might be a little naive?
      Pattern poBoxPattern = Pattern.compile("^PO\\sBOX\\s[0-9]+$");
      Matcher poBoxMatcher = poBoxPattern.matcher(poBox);
      return poBoxMatcher.matches() && (poBox.length() <= 25);
    } else {
      return true;
    }
  }
}
