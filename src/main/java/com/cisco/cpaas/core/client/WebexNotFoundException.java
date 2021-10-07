package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.ErrorResponse;

/**
 * Specific response exception that is thrown when the API returns a 404 while performing a GET of a
 * resource.
 */
public class WebexNotFoundException extends WebexResponseException {

  public WebexNotFoundException(String requestId, @Nullable ErrorResponse errorResponse) {
    super(requestId, 404, errorResponse);
  }
}
