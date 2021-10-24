package com.imiconnect.connect.whatsapp.type.template;

import com.imiconnect.connect.core.annotation.Nullable;
import lombok.Value;

import static java.util.Objects.requireNonNull;

/** Whatsapp template content that defines a currency amount to be templated into the message. */
@Value
public final class CurrencySubstitution implements Substitution {

  private final Type contentType = Type.CURRENCY;

  /**
   * The ISO4217-defined currency code specifying the type of currency. i.e. USD for United States
   * Dollars.
   */
  private final String code;

  /**
   * The amount of currency multiplied by 1000. The value held in this field should be
   * pre-multiplied. For example, if you want to represent $100.03, the value passed into this
   * object should be 100_030.
   */
  private final int amount1000;

  /**
   * The optional text that will be displayed to the user in cases where the API fails to process
   * the given currency amount and code.
   */
  private final String fallbackValue;

  public CurrencySubstitution(String code, int amount1000, @Nullable String fallbackValue) {
    this.code = requireNonNull(code, "currency code can not be null.");
    this.amount1000 = amount1000;
    this.fallbackValue = fallbackValue;
  }
}
