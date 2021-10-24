package com.imiconnect.connect.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link Preconditions}. */
class PreconditionsTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "+15551234567", // US
        "+4455512345678", // GBR
        "+1111234", // Smallest known (4 + country code)
        "+999123456789876" // Largest known (15)
      })
  public void shouldCheckE164PhoneNumbers(String number) {
    assertThat(Preconditions.isE164Endpoint(number), is(true));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "+123456", // Min 7 required
        "+015551234567", // Starts with 0
        "+1234567890123456" // Too large
      })
  public void shouldNotAcceptInvalidE164PhoneNumbers(String number) {
    assertThat(Preconditions.isE164Endpoint(number), is(false));
  }

  @Test
  public void shouldAcceptShortcode() {
    assertThat(Preconditions.isShortcodeEndpoint("34343"), is(true));
  }

  @Test
  public void shouldNotAcceptInvalidShortcode() {
    assertThat(Preconditions.isShortcodeEndpoint("+34343"), is(false));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "123456",
        "+15551234567",
      })
  public void shouldValidNumbers(String number) {
    assertThat(Preconditions.isEndpoint(number), is(true));
  }

  @Test
  public void shouldThrowIllegalArgumentWhenArgumentIsFalse() {
    IllegalArgumentException e =
        assertThrows(
            IllegalArgumentException.class, () -> Preconditions.validArgument(false, "message"));
    assertThat(e.getMessage(), containsString("message"));
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = "")
  public void shouldThrowWhenStringIsNullOrEmpty(String testValue) {
    IllegalArgumentException e =
        assertThrows(
            IllegalArgumentException.class, () -> Preconditions.notNullOrBlank(testValue, "name"));
    assertThat(e.getMessage(), containsString("name can not be null or blank"));
  }
}
