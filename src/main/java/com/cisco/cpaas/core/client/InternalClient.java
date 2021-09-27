package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.Idempotent;


/**
 * Defines the methods that are used to interact with all API services. The implementations of this
 * interface are intended to be for internal SDK use only. The contract may change at any time.
 */
public interface InternalClient extends WebexClient{

  /**
   * Send a GET request.
   *
   * @param id The identifier of the resource to retrieve.
   * @param responseType The type of response.
   * @param <R> The type of response.
   * @return The response object parsed into an instance of the responseType class. null will be
   *     returned in cases where the API returns a 404.
   * @throws WebexException When there is any issue communicating with API or (de)serializing the
   *     entities. Specific subtypes of the exception may be returned such as a
   *     ClientResponseException when communication with the API is successful, but it returned a
   *     non-successful (2xx) response.
   */
  @Nullable
  <R> R get(String id, Class<R> responseType) throws WebexException;

  /**
   * Send a POST request.
   *
   * @param request The request object to serialize as json and send as the payload in the post
   *     request.
   * @param responseType The type of response.
   * @param <R> The type of response.
   * @return The response object.
   * @throws WebexException When there is any issue communicating with API or (de)serializing the
   *     entities. Specific subtypes of the exception may be returned such as a
   *     ClientResponseException when communication with the API is successful, but it returned a
   *     non-successful (2xx) response.
   */
  <R> R post(Idempotent request, Class<R> responseType) throws WebexException;

}
