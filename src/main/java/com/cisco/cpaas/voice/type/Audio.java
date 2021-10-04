package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.voice.callback.action.PatchAction;

/** Base definition for the types of audio files that can be played in a voice message. */
public interface Audio {

  /** The type of audio. */
  public enum Type {
    TTS,
    MEDIA,
    URL
  }

  /** Gets the type of audio message. */
  public Type getType();

  /**
   * Repeat the audio this many times, this parameter is used for the greeting audio in a {@link
   * PatchAction}.
   */
  public Integer getLoop();
}
