package com.cisco.cpaas.voice.type;

import lombok.Builder;
import lombok.Value;

import java.net.URI;

/** Voice call audio that is located at a specific URL. */
@Value
@Builder(builderClassName = "Builder")
public class UrlAudio implements Audio {

  private final Type type = Type.URL;
  private final URI location;
  private final Integer loop;

  /**
   * Create a new url audio message with default values.
   *
   * @param location The url where the audio file is located.
   * @return The new audio object.
   */
  public static UrlAudio of(URI location) {
    return new UrlAudio(location, null);
  }
}
