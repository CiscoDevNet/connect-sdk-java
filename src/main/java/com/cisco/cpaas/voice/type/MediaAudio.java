package com.cisco.cpaas.voice.type;

import lombok.Builder;
import lombok.Value;

/**
 * Voice call audio that is created within the webex connect platform prior to being used and is
 * considered local to the webex platform.
 */
@Value
@Builder(builderClassName = "Builder")
public final class MediaAudio implements Audio {

  private final Type type = Type.MEDIA;
  private final String mediaId;
  private final Integer loop;

  /**
   * Creates a new Media audio file of the given ID and default values.
   * @param mediaId The ID specifying which media file to play.
   * @return The new MediaAudio object.
   */
  public static MediaAudio of(String mediaId) {
    return new MediaAudio(mediaId, null);
  }

}
