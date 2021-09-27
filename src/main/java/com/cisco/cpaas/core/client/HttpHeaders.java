package com.cisco.cpaas.core.client;

/** Collection of headers used to communicate with the webex api. */
interface HttpHeaders {

  /** The response header name that defines the request ID. */
  String REQUEST_ID = "Request-Id";

  /**
   * The request header name that is used to send the idempotency key to ensure a request is only
   * processed once.
   */
  String IDEMPOTENCY_KEY = "Idempotency-Key";

  /** Authentication header prefix for supporting bearer tokens. */
  String BEARER_PREFIX = "Bearer ";

  /** Standard authorization header name. */
  String AUTHORIZATION = "Authorization";
}
