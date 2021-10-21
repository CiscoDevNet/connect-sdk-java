package com.imiconnect.cpaas.core.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests to ensure the {@link TelephoneDigit} only accepts the allowed digits. */
class TelephoneDigitTest {

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "#"})
  public void shouldAcceptDtmfDigit(String digit) {
    TelephoneDigit.of(digit);
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "b", "c", "d", "!", "&", "'", ",", "."})
  public void shouldNotAcceptUnAllowedDigits(String digit) {
    assertThrows(IllegalArgumentException.class, () -> TelephoneDigit.of(digit));
  }
}
