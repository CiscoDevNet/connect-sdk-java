package com.imiconnect.connect.whatsapp.type;

import lombok.Value;

/** A physical location address that can be added to a WhatsApp {@link Contact}. */
@Value
@lombok.Builder(builderClassName = "Builder")
public final class Address {

  public enum Type {
    WORK,
    HOME
  }

  private final Type type;
  private final String street;
  private final String city;
  private final String state;
  private final String zip;
  private final String country;
  private final String countryCode;

  public Address(
      Type type,
      String street,
      String city,
      String state,
      String zip,
      String country,
      String countryCode) {
    this.type = type;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.country = country;
    this.countryCode = countryCode;
  }

  public static Builder asWork() {
    return new Builder().type(Type.WORK);
  }

  public static Builder asHome() {
    return new Builder().type(Type.HOME);
  }

  public static class Builder {}
}
