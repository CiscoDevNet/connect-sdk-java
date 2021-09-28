package com.cisco.cpaas.sms.type;

import lombok.Value;

/**
 * Represents a message placeholder substitution where the key is the templated value, and the value
 * is the actual value that will be substituted into the template.
 */
@Value(staticConstructor = "of")
public final class Substitution {

  private final String key;
  private final String value;
}
