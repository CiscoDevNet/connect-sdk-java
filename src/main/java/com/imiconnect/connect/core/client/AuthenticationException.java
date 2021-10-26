package com.imiconnect.connect.core.client;

import javax.annotation.Nullable;

/**
 * Specific response exception that is thrown when there is an issue authenticating with the rest
 * api.
 */
public class AuthenticationException extends HttpClientErrorException {
  public AuthenticationException(
      String requestId, int httpStatusCode, String reason, @Nullable String code) {
    super(requestId, httpStatusCode, reason, code);
  }
}
