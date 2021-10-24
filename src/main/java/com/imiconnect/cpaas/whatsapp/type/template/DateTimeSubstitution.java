package com.imiconnect.cpaas.whatsapp.type.template;

import com.imiconnect.cpaas.core.annotation.Nullable;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import static java.util.Objects.requireNonNull;

/** Whatsapp template content defining a datetime substitution. */
@Value
public final class DateTimeSubstitution implements Substitution {

  private final Type contentType = Type.DATETIME;

  /**
   * The date that will be displayed. If a time zone offset or Z is not provided in this value, the
   * time will be displayed the same in all time zones.
   *
   * <p>A LocalDateTime temporal can be used to create the date as a static value displayed in all
   * locales while an instant or
   */
  private final Temporal isoString;

  /**
   * The optional text that will be displayed to the user in cases where the API fails to process
   * the given isoString.
   */
  private final String fallbackValue;

  DateTimeSubstitution(Temporal isoString, @Nullable String fallbackValue) {
    this.isoString = requireNonNull(isoString, "datetime isoString can not be null.");
    this.fallbackValue = fallbackValue;
  }

  public static DateTimeSubstitution of(Instant dateTime, @Nullable String fallbackValue) {
    return new DateTimeSubstitution(dateTime, fallbackValue);
  }

  public static DateTimeSubstitution of(LocalDateTime dateTime, @Nullable String fallbackValue) {
    return new DateTimeSubstitution(dateTime, fallbackValue);
  }
}
