package com.imiconnect.connect.whatsapp.type.template;

import com.imiconnect.connect.core.type.StringWrapper;
import lombok.Value;

import static java.util.Objects.requireNonNull;

/** Whatsapp template content defining a plain text substitution. */
@Value
public final class TextSubstitution implements Substitution, StringWrapper {

  private final Type contentType = Type.TEXT;
  private final String text;

  public TextSubstitution(String text) {
    this.text = requireNonNull(text, "text value for substitution can not be null.");
  }

  @Override
  public String get() {
    return this.text;
  }
}
