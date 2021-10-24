package com.imiconnect.cpaas.whatsapp.type.template;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Defines a type of content that can be substituted into a template created on the whatsapp
 * platform.
 */
public interface Substitution {

  /** The different types of template content supported by the SDK. */
  enum Type {
    DATETIME,
    URL,
    CURRENCY,
    TEXT
  }

  public Type getContentType();

  /**
   * Creates new URL content for a whatsapp template where the base URL is defined within the Webex
   * Connect platform, and the suffix is defined here. The suffix is then appended to the to baseUrl
   * prior to sending out the message.
   *
   * @param suffix The URL suffix to be appended.
   * @return The new URL content.
   */
  public static UrlSubstitution ofUrlSuffix(String suffix) {
    return new UrlSubstitution(suffix);
  }

  /**
   * Creates a new text based substitution.
   *
   * @param text The text that will be substituted in the template.
   * @return The new TextSubstitution.
   */
  public static TextSubstitution ofText(String text) {
    return new TextSubstitution(text);
  }

  /**
   * Creates new currency content with the given code and amount.
   *
   * @param code The currency code. i.e. USD
   * @param amount The desired amount multiplied by 1000. i.e. 100_030 for $100.03.
   */
  public static CurrencySubstitution ofCurrency(String code, int amount) {
    return new CurrencySubstitution(code, amount, null);
  }

  /**
   * Creates a new DateTime substitution where the displayed value on the user's device will be
   * displayed statically on the user's device.
   *
   * @param localDateTime The instant representing the date and time to display.
   * @return DateTimeSubstitution
   */
  public static DateTimeSubstitution ofDateTime(LocalDateTime localDateTime) {
    return new DateTimeSubstitution(localDateTime, null);
  }

  /**
   * Creates a new DateTime substitution where the displayed value on the user's device will be
   * localized to their timezone.
   *
   * @param instant The instant representing the date and time to display.
   * @return DateTimeSubstitution
   */
  public static DateTimeSubstitution ofLocalizedDateTime(Instant instant) {
    return new DateTimeSubstitution(instant, null);
  }
}
