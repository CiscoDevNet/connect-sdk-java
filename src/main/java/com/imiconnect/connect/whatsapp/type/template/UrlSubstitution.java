package com.imiconnect.connect.whatsapp.type.template;

import lombok.Value;

import static java.util.Objects.requireNonNull;

/** Whatsapp template content that defines a URL substitution. */
@Value
public final class UrlSubstitution implements Substitution {

  private final Type contentType = Type.URL;

  /**
   * This will be appended to URLs that are configured within the Connect platform GUI.
   */
  private final String suffix;

  public UrlSubstitution(String suffix) {
    this.suffix = requireNonNull(suffix, "url suffix can not be null.");
  }

}
