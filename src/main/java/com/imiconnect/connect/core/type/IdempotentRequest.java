package com.imiconnect.connect.core.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Interface defining idempotent http requests that are used with the Connect platform API. */
@JsonIgnoreProperties({"idempotencyKey"})
public interface IdempotentRequest {

  /**
   * Returns an idempotency key that should not change for the life of the implementing object.
   * Returned value should not be more than 64 characters and match the pattern
   *
   * <pre>^[\w=\-]+$</pre>
   */
  public String getIdempotencyKey();
}
