package com.imiconnect.cpaas.core.type;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link PhoneNumber} that ensures it only accepts E164 phone numbers. */
class PhoneNumberTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "+15551234567", // US
        "+4455512345678", // GBR
        "+1111234", // Smallest known (4 + country code)
        "+999123456789876" // Largest known (15)
      })
  public void shouldAcceptE164PhoneNumbers(String number) {
    assertThat(com.imiconnect.cpaas.core.type.PhoneNumber.of(number).get(), is(number));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "+123456", // Min 7 required
        "+015551234567", // Starts with 0
        "+1234567890123456" // Too large
      })
  public void shouldNotAcceptInvalidE164PhoneNumbers(String number) {
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> com.imiconnect.cpaas.core.type.PhoneNumber.of(number));

    assertThat(e.getMessage(), containsString("not a valid E.164 phone number"));
  }

  @Test
  public void shouldHandleNullInput() {
    NullPointerException e = assertThrows(NullPointerException.class, () -> com.imiconnect.cpaas.core.type.PhoneNumber.of(null));
    assertThat(e.getMessage(), containsString("can not be null"));
  }
}
