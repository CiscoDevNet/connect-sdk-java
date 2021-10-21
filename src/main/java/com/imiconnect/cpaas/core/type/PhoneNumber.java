package com.imiconnect.cpaas.core.type;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.regex.Pattern;

import static com.imiconnect.cpaas.core.util.Preconditions.validArgument;
import static java.util.Objects.requireNonNull;

/**
 * Phone number that requires the value to be in E.164 format. i.e. +15557891234. Leading zeros on
 * the country code are not accepted and should be removed.
 *
 * @see <a href="https://www.itu.int/rec/T-REC-E.164/en">ITU E164 documentation</a>
 */
@ToString
@EqualsAndHashCode
public final class PhoneNumber implements StringWrapper {

  static final transient Pattern E164_VALIDATOR = Pattern.compile("^\\+[1-9]\\d{6,14}$");

  private final String number;

  private PhoneNumber(String number) {
    this.number = requireNonNull(number, "phone number can not be null.");
    validArgument(
        E164_VALIDATOR.matcher(number).matches(), number + " is not a valid E.164 phone number.");
  }

  public static PhoneNumber of(String number) {
    return new PhoneNumber(number);
  }

  /**
   * Gets the phone number as a plain string.
   *
   * @return The phone number, never null.
   */
  @Override
  public String get() {
    return number;
  }
}
