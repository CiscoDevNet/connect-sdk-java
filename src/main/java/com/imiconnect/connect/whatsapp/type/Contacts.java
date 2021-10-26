package com.imiconnect.connect.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;
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
  public static final class ContactNumber {

    /** The type of phone number. */
    public enum Type {
      HOME,
      WORK,
      CELL,
      MAIN,
      IPHONE
    }

    private final Type type;
    private final String number;
    private final String whatsAppId;

    public ContactNumber(Type type, String number, String whatsAppId) {
      this.type = type;
      this.number = number;
      this.whatsAppId = whatsAppId;
    }

    public static ContactNumber ofPhoneNumber(String phoneNumber, Type type) {
      return new ContactNumber(type, phoneNumber, null);
    }

    public static ContactNumber ofWhatsAppId(String id, Type type) {
      return new ContactNumber(type, null, id);
    }
  }

  /** A physical location address that can be added to a WhatsApp {@link Contact}. */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class Address {
    private final ContactType type;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;
    private final String countryCode;

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
  public static final class Email {
    private final ContactType type;
    private final String address;

    public Email(ContactType type, String address) {
      this.type = type;
      this.address = address;
    }

    public static Email ofWorkEmail(String email) {
      return new Email(ContactType.WORK, email);
    }

    public static Email ofHomeEmail(String email) {
      return new Email(ContactType.HOME, email);
    }

  }

  /** Optional URL that can be added to a WhatsApp {@link Contact}. */
  @Value
  public static final class Url {
    private final ContactType type;
    private final URI address;

    public Url(ContactType type, URI address) {
      this.type = type;
      this.address = address;
    }

    public static Url ofWorkUrl(String url) {
      return new Url(ContactType.WORK, URI.create(url));
    }

    public static Url ofHomeUrl(String url) {
      return new Url(ContactType.HOME, URI.create(url));
    }
  }

  /** The type of WhatsApp {@link Contact}. */
  public enum ContactType {
    HOME,
    WORK
  }
}
