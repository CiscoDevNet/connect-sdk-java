package com.imiconnect.connect.whatsapp.type;

import lombok.Value;

import java.net.URI;

/** Optional URL that can be added to a WhatsApp {@link Contact}. */
@Value
public final class ContactUrl {

  public enum Type {
    WORK,
    HOME
  }

  private final Type type;
  private final URI address;

  public ContactUrl(Type type, URI address) {
    this.type = type;
    this.address = address;
  }

  public static ContactUrl ofWorkUrl(String url) {
    return new ContactUrl(Type.WORK, URI.create(url));
  }

  public static ContactUrl ofHomeUrl(String url) {
    return new ContactUrl(Type.HOME, URI.create(url));
  }
}
