package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.annotation.Nullable;
import lombok.Value;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/** Voice call audio that is located at a specific URL. */
@Value
public class UrlAudio implements Audio {

  private final Type type = Type.URL;
  private final URI location;
  private final Integer loop;

  public UrlAudio(URI location, @Nullable Integer loop) {
    this.location = requireNonNull(location, "location URL can not be null.");
    this.loop = loop;
  }
}
