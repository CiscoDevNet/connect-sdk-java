package com.imiconnect.connect.core.parser;

import com.imiconnect.connect.core.ConnectException;

/** Exception thrown when either the request or response could not be (de)serialized. */
public class ParseException extends ConnectException {

  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
