package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.type.ErrorResponse;

/**
 * Exception that is thrown when the request was successfully sent, but the service returned a non-
 * successful http status response. In some cases, a 404 response may not produce this exception.
 * See the webex client interfaces for more information.
 */
public class WebexResponseException extends WebexException {

  private final String requestId;
  private final int httpStatusCode;
  private final ErrorResponse errorResponse;

  public WebexResponseException(String requestId, int httpStatusCode, ErrorResponse errorResponse) {
    super(errorResponse.getCode() + " - " + errorResponse.getMessage());
    this.requestId = requestId;
    this.httpStatusCode = httpStatusCode;
    this.errorResponse = errorResponse;
  }

  /**
   * The unique request ID that identifies the specific request that was sent. This is typically
   * used for support and debugging purposes.
   *
   * @return the request's ID.
   */
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
   * @return the error code.
   */
  public String getErrorCode() {
    return errorResponse.getCode();
  }
}
