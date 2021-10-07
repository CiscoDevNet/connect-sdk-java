package com.cisco.cpaas.core.type;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

import static com.cisco.cpaas.core.util.Preconditions.validArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a single key on a standard telephone keypad where there are numbers 1-9, <code>*
 * </code>, and <code>#</code>
 */
@ToString
@EqualsAndHashCode
public final class TelephoneDigit implements StringWrapper {

  private static final String DTMF_DIGITS = "0123456789*#";
  private static final Set<String> ALLOWED_CHAR_SET =
      DTMF_DIGITS.chars().mapToObj(d -> String.valueOf((char) d)).collect(Collectors.toSet());

  private final String digit;

  private TelephoneDigit(String digit) {
    this.digit = requireNonNull(digit, "digit can not be null");
    validArgument(
        ALLOWED_CHAR_SET.contains(digit), "Invalid DTMF digit. Must be a single digit, '*' or '#'");
  }

  public static TelephoneDigit of(String digit) {
    return new TelephoneDigit(digit);
  }

  public String get() {
    return digit;
  }
}
