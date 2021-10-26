package com.imiconnect.connect.core.client;

/**
 * Specific response exception that is thrown when the API returns a 404 while performing a GET of a
 * resource.
 */
public class ResourceNotFoundException extends HttpClientErrorException {

  public ResourceNotFoundException(String requestId) {
    super(requestId, 404, "Resource not found.", null);
  }
}
