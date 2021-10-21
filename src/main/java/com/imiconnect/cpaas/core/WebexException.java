package com.imiconnect.cpaas.core;

import com.imiconnect.cpaas.core.annotation.Nullable;

/**
 * Generic exception that can be thrown from any of the clients when an unresolvable error occurs.
 */
public class WebexException extends RuntimeException {

  public WebexException(Throwable cause) {
    super(cause);
  }

  public WebexException(String message) {
    this(message, null);
  }

  public WebexException(String message, @Nullable Throwable cause) {
    super(message, cause);
  }
}
