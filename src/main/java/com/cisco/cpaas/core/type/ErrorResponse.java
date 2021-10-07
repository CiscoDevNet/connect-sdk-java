package com.cisco.cpaas.core.type;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/** Pojo that maps to the json messages that would be returned by an error on the API. */
@ToString
@EqualsAndHashCode
public final class ErrorResponse {

  /** The domain specific error code. */
  private final String code;

  /** A short description of the error. */
   private final String message;

  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Get the domain specific error code.
   * @return The error code.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Get the domain specific error message.
   * @return The error message.
   */
  public String getMessage() {
    return this.message;
  }

}
