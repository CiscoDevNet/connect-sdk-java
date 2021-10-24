package com.imiconnect.cpaas.core;

import com.imiconnect.cpaas.core.annotation.Nullable;

/**
 * Generic exception that can be thrown from any of the clients when an unresolvable error occurs.
 */
public class ConnectException extends RuntimeException {

  public ConnectException(Throwable cause) {
    super(cause);
  }

  public ConnectException(String message) {
    this(message, null);
  }

  public ConnectException(String message, @Nullable Throwable cause) {
    super(message, cause);
  }
}
