package com.imiconnect.connect.whatsapp.type;

import lombok.Value;

/** A phone number that can be added to a WhatsApp {@link Contact}. */
@Value
public final class ContactNumber {

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
