package com.imiconnect.connect.voice.type;

import lombok.Value;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/** Voice call audio that is located at a specific URL. */
@Value
public class UrlAudio implements Audio {

  private final Type type = Type.URL;
  private final URI location;

  public UrlAudio(URI location) {
    this.location = requireNonNull(location, "location URL can not be null.");
  }
}
