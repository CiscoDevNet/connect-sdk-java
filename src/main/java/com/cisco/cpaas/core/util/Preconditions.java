package com.cisco.cpaas.core.util;

import com.cisco.cpaas.core.annotation.Nullable;

import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/** Convenience methods for validating arguments passed into a method or constructor. */
// TODO: Move the phone number validators out of here.
public class Preconditions {

  private static final Pattern SHORTCODE_VALIDATOR = Pattern.compile("(^\\d{1,6}$)");
  private static final Pattern E164_VALIDATOR = Pattern.compile("(^\\+[1-9]\\d{6,14}$)");

  /**
   * Checks if a phone number is in E.164 format.
   *
   * @param number The number to check.
   * @return true if it is in E.164 format.
   */
  public static boolean isE164Endpoint(String number) {
    requireNonNull(number, "number can not be null");
    return E164_VALIDATOR.matcher(number).matches();
  }

  /**
   * Checks if a phone number is a shortcode defined as a string of less than 7 digits.
   *
   * @param number The number to check.
   * @return true if it is within the definition of a shortcode.
   */
  public static boolean isShortcodeEndpoint(String number) {
    requireNonNull(number, "number can not be null");
    return SHORTCODE_VALIDATOR.matcher(number).matches();
  }

  /**
   * Checks if a phone number is a shortcode or an E.164 formatted telephone number.
   *
   * @param number The number to check.
   * @return true if it is a shortcode or E.164 number.
   */
  public static boolean isEndpoint(String number) {
    return isShortcodeEndpoint(number) || isE164Endpoint(number);
  }

  /**
   * Ensures the given boolean argument evaluates to true.
   *
   * @param argument The argument to test.
   * @param errorMsg The error message to be thrown when the test argument is false.
   * @throws IllegalArgumentException when the test argument evaluates to false.
   */
  public static void validArgument(boolean argument, String errorMsg) {
    if (!argument) {
      throw new IllegalArgumentException(errorMsg);
    }
  }

  /**
   * Ensures that the string value is not null and has at least 1 character.
   *
   * @param value The string to test.
   * @param name THe name of the property being tested. Used to construct the exception message.
   * @throws IllegalArgumentException when the value is null or blank.
   */
  public static String notNullOrBlank(@Nullable String value, String name) {
    if (value == null || value.length() < 1) {
      throw new IllegalArgumentException(name + " can not be null or blank.");
    }
    return value;
  }
}
