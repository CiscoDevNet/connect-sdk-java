package com.imiconnect.connect.core.client;

/** Thrown when the server returns a 500 Internal server error response. */
public class InternalServerErrorException extends HttpServerErrorException {

  public InternalServerErrorException(String requestId, String reason, String code) {
    super(requestId, 500, reason, code);
  }
}
