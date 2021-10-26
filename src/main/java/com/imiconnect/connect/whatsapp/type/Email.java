package com.imiconnect.connect.whatsapp.type;

import lombok.Value;

/** Optional email address that can be added to a WhatsApp {@link Contact}. */
@Value
public final class Email {

  public enum Type {
    WORK,
    HOME
  }

  private final Type type;
  private final String address;

  public Email(Type type, String address) {
    this.type = type;
    this.address = address;
  }

  public static Email ofWorkEmail(String email) {
    return new Email(Type.WORK, email);
  }

  public static Email ofHomeEmail(String email) {
    return new Email(Type.HOME, email);
  }
}
