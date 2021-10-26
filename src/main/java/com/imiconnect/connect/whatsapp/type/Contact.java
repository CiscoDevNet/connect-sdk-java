package com.imiconnect.connect.whatsapp.type;

import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * A specific contact that can be attached to a {@link WhatsAppMsg} containing the {@link Contacts}
 * type of content.
 */
@Value
@lombok.Builder(builderClassName = "Builder")
public final class Contact {

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
  @Singular private final List<Address> addresses;
  @Singular private final List<Email> emails;
  @Singular private final List<ContactUrl> urls;

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
      List<Address> addresses,
      List<Email> emails,
      List<ContactUrl> urls) {
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
