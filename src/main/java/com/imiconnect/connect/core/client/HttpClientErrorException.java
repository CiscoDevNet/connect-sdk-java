package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.annotation.Nullable;

/**
 * Thrown when the server returns a response with a status code in the 4xx range indicating an issue
 * with the request itself.
 */
public class HttpClientErrorException extends HttpResponseException {
  public HttpClientErrorException(
      String requestId, int statusCode, String reason, @Nullable String errorCode) {
    super(requestId, statusCode, reason, errorCode);
  }
}
