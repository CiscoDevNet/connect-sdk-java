package com.imiconnect.connect.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link Preconditions}. */
class PreconditionsTest {

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
