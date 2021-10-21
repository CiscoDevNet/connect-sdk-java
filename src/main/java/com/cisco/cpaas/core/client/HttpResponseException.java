package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.ErrorResponse;

import static java.util.Objects.nonNull;

/**
 * Exception that is thrown when the request was successfully sent, but the service returned a non-
 * successful http status response. In some cases, a 404 response may not produce this exception.
 * See the webex client interfaces for more information.
 */
public class HttpResponseException extends WebexException {

  private final String requestId;
  private final int httpStatusCode;
  private final String errorCode;

  public HttpResponseException(
      String requestId, int httpStatusCode, @Nullable ErrorResponse errorResponse) {
    super(constructMessage(httpStatusCode, errorResponse));
    this.requestId = requestId;
    this.httpStatusCode = httpStatusCode;
    this.errorCode = nonNull(errorResponse) ? errorResponse.getCode() : null;
  }

  private static String constructMessage(int httpStatusCode, ErrorResponse error) {
    if (error == null) {
      return "HTTP status: " + httpStatusCode;
    }
    return String.format("%s - %s", error.getCode(), error.getMessage());
//    return String.format("%d: %s - %s", httpStatusCode, error.getCode(), error.getMessage());
  }

  /**
   * The unique request ID that identifies the specific request that was sent. This is typically
   * used for support and debugging purposes.
   *
   * @return the request's ID, may be null.
   */
  @Nullable
  public String getRequestId() {
    return requestId;
  }

  /**
   * The http status code returned from the service that caused this error to be thrown.
   *
   * @return the http status code.
   */
  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  /**
   * The domain specific error code associated with the error response. See the webex API
   * documentation for more information.
   *
   * @return the error code, may be null.
   */
  @Nullable
  public String getErrorCode() {
    return errorCode;
  }
}
