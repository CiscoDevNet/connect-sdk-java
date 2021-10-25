package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.annotation.Nullable;

/** Exception to be thrown when the API returns a 400 Bad request. */
public class BadRequestException extends HttpClientErrorException {
  public BadRequestException(String requestId, String reason, @Nullable String code) {
    super(requestId, 400, reason, code);
  }
}
