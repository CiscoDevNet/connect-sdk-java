package com.imiconnect.connect.core.client;

import javax.annotation.Nullable;

/** Thrown when the rate limit associated with an account has been reached for a given period. */
public class RateLimitExceededException extends HttpClientErrorException {

  public RateLimitExceededException(String requestId, String reason, @Nullable String errorCode) {
    super(requestId, 429, reason, errorCode);
  }
}
