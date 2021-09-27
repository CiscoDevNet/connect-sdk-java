package com.cisco.cpaas.core.type;

/** Pojo that maps to the json messages that would be returned by an error on the API. */
public final class ErrorResponse {

  /** The domain specific error code. */
  private final String code;

  /** A short description of the error. */
  private final String message;

  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
