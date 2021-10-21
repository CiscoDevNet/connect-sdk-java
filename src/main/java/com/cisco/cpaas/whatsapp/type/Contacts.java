package com.cisco.cpaas.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cisco.cpaas.core.util.Preconditions.validArgument;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/** Defines whatsapp {@link Content} that represents a contact message. */
@Value
@Builder(builderClassName = "Builder")
public final class Contacts implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.CONTACTS;
  @Singular private final List<Contacts.Contact> contacts;

  @JsonCreator
  private Contacts(List<Contacts.Contact> contacts) {
    this.contacts = requireNonNull(contacts, "contacts can not be null");
    validArgument(contacts.size() > 0, "There must be at least one contact.");
  }

  /**
   * Convenience method to create a new Contacts content with a single contact entry.
   *
   * @param contact The single contact to add.
   * @return A new Contacts content with the single contact added.
   */
  public static Contacts of(Contacts.Contact contact) {
    List<Contacts.Contact> contacts = new ArrayList<>(1);
    contacts.add(contact);
    return new Contacts(Collections.unmodifiableList(contacts));
  }

  /**
   * A specific contact that can be attached to a {@link WhatsAppMsg} containing the {@link
   * Contacts} type of content.
   */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class Contact {

    private final String formattedName;
    private final String namePrefix;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String nameSuffix;
    private final LocalDate birthday;
    private final String company;
    private final String department;
    private final String title;
    @Singular private final List<ContactNumber> phones;
    @Singular private final List<Contacts.Address> addresses;
    @Singular private final List<Contacts.Email> emails;
    @Singular private final List<Contacts.Url> urls;

    private Contact(
        String formattedName,
        String namePrefix,
        String firstName,
        String middleName,
        String lastName,
        String nameSuffix,
        LocalDate birthday,
        String company,
        String department,
        String title,
        List<ContactNumber> phones,
        List<Contacts.Address> addresses,
        List<Contacts.Email> emails,
        List<Contacts.Url> urls) {
      this.formattedName = requireNonNull(formattedName, "Formatted name is required");
      this.namePrefix = namePrefix;
      this.firstName = firstName;
      this.middleName = middleName;
      this.lastName = lastName;
      this.nameSuffix = nameSuffix;
      this.birthday = birthday;
      this.company = company;
      this.department = department;
      this.title = title;
      this.phones = phones;
      this.addresses = addresses;
      this.emails = emails;
      this.urls = urls;

      validArgument(
          nonNull(firstName)
              || nonNull(lastName)
              || nonNull(middleName)
              || nonNull(namePrefix)
              || nonNull(nameSuffix),
          "At least one of first, last, middle, suffix, or prefix is required.");
    }
  }

  /** A phone number that can be added to a WhatsApp {@link Contact}. */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class ContactNumber {

    /** The type of phone number. */
    public enum Type {
      HOME,
      WORK,
      CELL,
      MAIN,
      IPHONE
    }

    private Type type;
    private String number; // TODO, make this an e164 endpoint object
    private String whatsAppId;
  }

  /** A physical location address that can be added to a WhatsApp {@link Contact}. */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class Address {
    private ContactType type;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String countryCode;

    /**
     * Formats the string in a human-readable form that is typically used in the US. This can be
     * used to construct a string for {@link Location} addresses if desired.
     *
     * @return The formatted address.
     */
    public String toFormattedString() {
      return String.format("%s, %s, %s %s %s", street, city, state, zip, country);
    }
  }

  /** Optional email address that can be added to a WhatsApp {@link Contact}. */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class Email {
    private ContactType type;
    private String address; // TODO: Add basic email validation?
  }

  /** Optional URL that can be added to a WhatsApp {@link Contact}. */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class Url {
    private ContactType type;
    private URI address;
  }

  /** The type of WhatsApp {@link Contact}. */
  public enum ContactType {
    HOME,
    WORK
  }
}
