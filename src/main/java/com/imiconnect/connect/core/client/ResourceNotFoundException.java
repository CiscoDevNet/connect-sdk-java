package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.annotation.Nullable;
import com.imiconnect.connect.core.type.ErrorResponse;

/**
 * Specific response exception that is thrown when the API returns a 404 while performing a GET of a
 * resource.
 */
public class ResourceNotFoundException extends HttpResponseException {

  public ResourceNotFoundException(String requestId, @Nullable ErrorResponse errorResponse) {
    super(requestId, 404, errorResponse);
  }
}
