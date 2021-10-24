package com.imiconnect.cpaas.core.client;

import com.imiconnect.cpaas.core.ConnectException;
import com.imiconnect.cpaas.core.type.Idempotent;

/**
 * Defines the methods that are used to interact with all API services. The implementations of this
 * interface are intended to be for internal SDK use only. The contract may change at any time.
 */
public interface InternalClient extends ConnectClient {

  /**
   * Send a GET request.
   *
   * @param path The path that is appended to the base url.
   * @param responseType The type of response.
   * @param <R> The type of response.
   * @return The response object parsed into an instance of the responseType class.
   * @throws ConnectException When there is any issue communicating with API or (de)serializing the
   *     entities. Specific subtypes of the exception may be returned such as a
   *     ClientResponseException when communication with the API is successful, but it returned a
   *     non-successful (2xx) response.
   */
  <R extends ConnectResponse> R get(String path, Class<R> responseType) throws ConnectException;

  /**
   * Send a POST request.
   *
   * @param path The path that is appended to the base url.
   * @param request The request object to serialize as json and send as the payload in the post
   *     request.
   * @param responseType The type of response.
   * @param <R> The type of response.
   * @return The response object.
   * @throws ConnectException When there is any issue communicating with API or (de)serializing the
   *     entities. Specific subtypes of the exception may be returned such as a
   *     ClientResponseException when communication with the API is successful, but it returned a
   *     non-successful (2xx) response.
   */
  <R extends ConnectResponse> R post(String path, Idempotent request, Class<R> responseType)
      throws ConnectException;
}
