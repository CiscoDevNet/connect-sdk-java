package com.imiconnect.connect.core.util;

import javax.annotation.Nullable;

/** Convenience methods for validating arguments passed into a method or constructor. */
public class Preconditions {

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
