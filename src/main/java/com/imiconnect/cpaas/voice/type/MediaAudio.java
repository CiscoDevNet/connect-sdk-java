package com.imiconnect.cpaas.voice.type;

import lombok.Value;

/**
 * Voice call audio that is created within and local to the Connect platform. A media ID is
 * presented in the GUI which can be used with this class.
 */
@Value
public final class MediaAudio implements Audio {

  private final Type type = Type.MEDIA;
  private final String mediaId;

  public MediaAudio(String mediaId) {
    this.mediaId = mediaId;
  }
}
