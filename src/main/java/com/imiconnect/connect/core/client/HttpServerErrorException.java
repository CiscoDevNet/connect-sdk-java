package com.imiconnect.connect.core.client;

import javax.annotation.Nullable;

/**
 * Thrown when the server returns a response with a status code in the 5xx range and can typically
 * be retried on.
 */
public class HttpServerErrorException extends HttpResponseException {
  public HttpServerErrorException(
      String requestId, int statusCode, String reason, @Nullable String code) {
    super(requestId, statusCode, reason, code);
  }
}
