package com.cisco.cpaas.core.type;

import lombok.Value;

/** Pojo that maps to the json messages that would be returned by an error on the API. */
@Value
public final class ErrorResponse {

  /** The domain specific error code. */
  private final String code;

  /** A short description of the error. */
  private final String message;

}
