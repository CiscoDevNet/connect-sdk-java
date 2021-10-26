package com.imiconnect.connect.core.client;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * Base api response object that contains only the requestId that would come in as a header on the
 * http response.
 */
@ToString
@EqualsAndHashCode
public abstract class ConnectResponse {

  private String requestId;

  protected ConnectResponse(String requestId) {
    this.requestId = requestId;
  }

  /** Sets the request ID. This is intended to be used only by the internal http client. */
  void setRequestId(@Nullable String requestId) {
    this.requestId = requestId;
  }

  /**
   * Gets the request ID that is unique to the specific request /response exchange that resulted in
   * this object. Generally useful for debugging purposes or technical support requests.
   *
   * @return The request ID.
   */
  public String getRequestId() {
    return requestId;
  }
}
