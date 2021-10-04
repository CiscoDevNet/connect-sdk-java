package com.cisco.cpaas.core.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Interface defining methods used to determine the idempotency of a particular object. */
@JsonIgnoreProperties({"idempotencyKey"})
public interface Idempotent {

  /**
   * Returns an idempotency key that should not change for the life of the implementing object.
   * Returned value should not be more than 64 characters and match the pattern
   *
   * <pre>^[\w=\-]+$</pre>
   */
  public String getIdempotencyKey();
}
