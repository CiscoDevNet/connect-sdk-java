package com.imiconnect.connect.core.util;

import static java.util.Objects.requireNonNull;

/**
 * Utility to check if a string contains unicode literal characters or not. Unicode hexadecimal
 * escape sequences and literal unicode characters are supported.
 *
 * <p>Some examples of properly detected unicode strings:
 *
 * <pre>
 *   My text! \u00A9 --copyright sign as hex sequence
 *   My text! © --literal copyright sign
 *   My text! \uD83D\uDE00 --This is a smiley emoji using a surrogate pair
 * </pre>
 */
public class UnicodeDetector {

  public static boolean containsUnicode(String input) {
    requireNonNull(input, "input can not be null to detect unicode");
    return input.chars().anyMatch(i -> i > 127);
  }
}
