package com.imiconnect.connect.voice.callback.action;

import com.imiconnect.connect.core.type.TelephoneDigit;
import com.imiconnect.connect.voice.type.Audio;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Plays one or more audio clips and records the conversation until the timer has run out or the
 * termination digit has been pressed.
 */
@Value
@Builder(builderClassName = "Builder")
public final class RecordAction implements Action {

  private final Type action = Type.RECORD;

  /** The sequence of audio clips to play. Array order reflects order that the clips are played. */
  private final List<Audio> audio;

  /** The recording stops after this many seconds. */
  private final Integer timeoutSeconds;

  /** The recording is stopped after this DTMF digit is pressed. */
  private final TelephoneDigit terminationDigit;
}
