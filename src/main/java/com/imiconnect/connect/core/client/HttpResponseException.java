package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.ConnectException;

import javax.annotation.Nullable;

/**
 * Exception that is thrown when the request was successfully sent, but the service returned a non-
 * successful http status response.
 */
public class HttpResponseException extends ConnectException {

  private final String requestId;
  private final int statusCode;
  private final String errorCode;

  public HttpResponseException(
      String requestId, int statusCode, String reason, @Nullable String errorCode) {
    super(constructMsg(statusCode, reason, errorCode));
    this.requestId = requestId;
    this.statusCode = statusCode;
    this.errorCode = errorCode;
  }

  public HttpResponseException(String requestId, int statusCode, String reason) {
    this(requestId, statusCode, reason, (Throwable) null);
  }

  public HttpResponseException(String requestId, int statusCode, String reason, Throwable t) {
    super(constructMsg(statusCode, reason, null), t);
    this.requestId = requestId;
    this.statusCode = statusCode;
    this.errorCode = null;
  }

  private static String constructMsg(int statusCode, String reason, @Nullable String errorCode) {
    String code = errorCode == null ? String.valueOf(statusCode) : errorCode;
    return String.format("%s - %s", code, reason);
  }

  /**
   * The unique request ID that identifies the specific request that was sent. This is typically
   * used for support and debugging purposes.
   *
   * @return the request's ID, may be null.
   */
  public String getRequestId() {
    return requestId;
  }

  /**
   * The http status code returned from the service that caused this error to be thrown.
   *
   * @return the http status code.
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * The domain specific error code associated with the error response. See the Connect API
   * documentation for more information.
   *
   * @return the error code, may be null.
   */
  @Nullable
  public String getErrorCode() {
    return errorCode;
  }
}
