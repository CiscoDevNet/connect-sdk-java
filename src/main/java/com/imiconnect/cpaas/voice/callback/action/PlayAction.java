package com.imiconnect.cpaas.voice.callback.action;

import com.imiconnect.cpaas.core.type.TelephoneDigit;
import com.imiconnect.cpaas.voice.type.Audio;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * Plays one or more audio clips to the dialed number, optionally recording the call and collecting
 * a sequence of pressed DTMF digits.
 */
@Value
@Builder(builderClassName = "Builder")
public final class PlayAction implements Action {

  private final Type action = Type.PLAY;

  /** When true, the call audio will be recorded. */
  private final Boolean recordCall;

  /**
   * The sequence of audio clips to play. At least one is required and array order reflects order
   * that the clips are played.
   */
  @NonNull private final List<Audio> audio;

  /**
   * If positive, allows a caller to enter this many DTMF digits from the phone keypad. Defaults to
   * zero.
   */
  private final Integer maxDigits;

  /**
   * Stop collecting digits if this many seconds elapses without additional digits being entered.
   * Defaults to five seconds.
   */
  private final Integer digitTimeout;

  /**
   * If present, allow a caller to terminate their DTMF digit entry by pressing this character.
   * Default is null.
   */
  private final TelephoneDigit terminationDigit;
}
