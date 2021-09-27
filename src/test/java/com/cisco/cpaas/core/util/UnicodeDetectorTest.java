package com.cisco.cpaas.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/** Unit tests for the {@link UnicodeDetector}. */
class UnicodeDetectorTest {

  // CHECKSTYLE:OFF
  private static final String ASCII_CHARSET =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890-=!@#$%^&*()_+`~,./<>?[]\\{}|;':";
  // CHECKSTYLE:ON

  @Test
  public void shouldAsciiOnly() {
    assertThat(UnicodeDetector.containsUnicode(ASCII_CHARSET), is(false));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "Copyright © symbol",
        "Copyright \u00AE symbol",
        "emoji \uD83D\uDE00",
        "japanese ⿓⿔⿕, ぁあぃい, ァアィ"
      })
  public void shouldDetectUnicode(String text) {
    assertThat(UnicodeDetector.containsUnicode(text), is(true));
    System.out.println(text);
  }
}
