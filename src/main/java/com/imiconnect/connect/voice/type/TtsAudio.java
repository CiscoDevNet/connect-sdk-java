package com.imiconnect.connect.voice.type;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.imiconnect.connect.voice.engine.Voice;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

import static com.imiconnect.connect.core.util.Preconditions.notNullOrBlank;

/** Voice call audio files that are generated by a text to speech system. */
@Value
@Builder(builderClassName = "Builder")
public final class TtsAudio implements Audio {

  /** The format of the data in the text field. */
  public enum TextFormat {
    /** Plain text. */
    TEXT,
    /** Speech Synthesis Markup language text. */
    SSML
  }

  private final Type type = Type.TTS;
  private final String text;
  private final TextFormat textFormat;
  @JsonUnwrapped private final Voice voice;

  TtsAudio(String text, @Nullable TextFormat textFormat, @Nullable Voice voice) {
    this.text = notNullOrBlank(text, "text");
    this.textFormat = textFormat;
    this.voice = voice;
  }
}
