package com.imiconnect.connect.voice.type;

import lombok.Value;

import java.net.URI;

/** Metadata describing a recording taken during a call. */
@Value
public final class Recording {

  /** The length of the recording. */
  private final Integer durationSeconds;

  /** Location where the audio file can be accessed. */
  private final URI url;
}
