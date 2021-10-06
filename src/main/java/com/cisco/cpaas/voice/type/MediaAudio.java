package com.cisco.cpaas.voice.type;

import lombok.Value;

/**
 * Voice call audio that is created within the webex connect platform prior to being used and is
 * considered local to the webex platform.
 */
@Value
public final class MediaAudio implements Audio {

  private final Type type = Type.MEDIA;
  private final String mediaId;
  private final Integer loop;

  public MediaAudio(String mediaId, Integer loop) {
    this.mediaId = mediaId;
    this.loop = loop;
  }

}
