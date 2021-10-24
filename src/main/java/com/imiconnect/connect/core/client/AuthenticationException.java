package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.type.ErrorResponse;

/**
 * Specific response exception that is thrown when there is an issue authenticating with the rest
 * api.
 */
public class AuthenticationException extends HttpResponseException {
  public AuthenticationException(
      String requestId, int httpStatusCode, ErrorResponse errorResponse) {
    super(requestId, httpStatusCode, errorResponse);
  }
}
