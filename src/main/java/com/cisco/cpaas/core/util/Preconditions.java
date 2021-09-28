package com.cisco.cpaas.core.util;

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

  public static void validArgument(boolean argument, String errorMsg) {
    if (!argument) {
      throw new IllegalArgumentException(errorMsg);
    }
  }
}
