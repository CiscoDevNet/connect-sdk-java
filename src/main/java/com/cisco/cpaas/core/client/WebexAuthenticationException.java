package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.type.ErrorResponse;

/**
 * Specific response exception that is thrown when there is an issue authenticating with the rest
 * api.
 */
public class WebexAuthenticationException extends WebexResponseException {
  public WebexAuthenticationException(
      String requestId, int httpStatusCode, ErrorResponse errorResponse) {
    super(requestId, httpStatusCode, errorResponse);
  }
}
