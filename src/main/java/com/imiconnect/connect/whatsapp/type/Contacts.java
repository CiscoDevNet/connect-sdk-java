package com.imiconnect.connect.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;
import static java.util.Objects.requireNonNull;

/** Defines whatsapp {@link Content} that represents a contact message. */
@Value
public final class Contacts implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.CONTACTS;
  private final List<Contact> contacts;

  @JsonCreator
  public Contacts(List<Contact> contacts) {
    requireNonNull(contacts, "contacts can not be null");
    this.contacts = Collections.unmodifiableList(contacts);
    validArgument(contacts.size() > 0, "There must be at least one contact.");
  }

  /**
   * Convenience method to create a new Contacts content with a single contact entry.
   *
   * @param contact The single contact to add.
   * @return A new Contacts content with the single contact added.
   */
  public static Contacts of(Contact contact) {
    List<Contact> contacts = new ArrayList<>(1);
    contacts.add(contact);
    return new Contacts(contacts);
  }
}
