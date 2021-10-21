package com.imiconnect.cpaas.core.parser;

import com.imiconnect.cpaas.core.WebexException;

/** Exception thrown when either the request or response could not be (de)serialized. */
public class WebexParseException extends WebexException {

  public WebexParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
