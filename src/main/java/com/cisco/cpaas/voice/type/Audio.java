package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.voice.callback.action.PatchAction;
import com.cisco.cpaas.voice.engine.Voice;

import java.net.URI;

/** Definition for the types of audio files that can be played in a voice message. */
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

  /**
   * Create a new {@link UrlAudio} message with default values.
   *
   * @param location The url where the audio file is located.
   * @return The new audio object.
   */
  public static UrlAudio ofUrl(URI location) {
    return new UrlAudio(location, null);
  }

  /**
   * Creates a new {@link MediaAudio} message of the given ID and default values.
   *
   * @param mediaId The ID specifying which media file to play.
   * @return The new MediaAudio object.
   */
  public static MediaAudio ofMediaId(String mediaId) {
    return new MediaAudio(mediaId, null);
  }

  /**
   * Create a new {@link TtsAudio} message with default values.
   *
   * @param text The dialog that will be spoken.
   * @param voice The voice that will dictate the dialog to the callee.
   * @return The new audio object.
   */
  public static TtsAudio ofTtsText(String text, @Nullable Voice voice) {
    return new TtsAudio(text, TtsAudio.TextFormat.TEXT, null, voice);
  }

  /**
   * Create a new {@link TtsAudio} using SSML formatted text and default values.
   *
   * @param ssml The SSML that will be used to dictate the message.
   * @param voice The voice that will dictate the dialog to the callee.
   * @return The new audio object.
   */
  public static TtsAudio ofTtsSsml(String ssml, @Nullable Voice voice) {
    return new TtsAudio(ssml, TtsAudio.TextFormat.SSML, null, voice);
  }
}
