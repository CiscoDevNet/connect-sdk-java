package com.cisco.cpaas.voice.callback.action;

import com.cisco.cpaas.core.type.PhoneNumber;
import com.cisco.cpaas.core.type.TelephoneDigit;
import com.cisco.cpaas.voice.type.Audio;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/** Patches the current call session to another separate session. */
@Value
@Builder(builderClassName = "Builder")
public class PatchAction implements Action {

  private final Type action = Type.PATCH;

  /** When true, the call audio will be recorded. */
  private final Boolean recordCall;

  /**
   * This audio clip is played to the existing call leg while the platform is connecting the new
   * call leg.
   */
  private final Audio holdAudio;

  /**
   * This audio object is played to the new call leg before patching them to the existing call leg.
   */
  private final Audio greetingAudio;

  /** The calling party number when dialing the new call leg. */
  private final PhoneNumber patchCallerId;

  /** Dial this number to patch on to the existing call. */
  @NonNull private final PhoneNumber dialedNumber;

  /**
   * If present, called party must press this digit to be patched to the existing call, otherwise
   * they will be patched once the greeting audio has completed playing. Defaults to null.
   */
  private final TelephoneDigit patchDigit;

  /**
   * If enabled, DTMF signalling will be passed between the two patched call legs. Default to true.
   */
  private final Boolean passDtmf;

  /**
   * Specifies how many times the greeting message will be played to the called party. Default value
   * is <code>0</code> meaning the message will be played one time.
   */
  private final Integer greetingRepeatCount;
}
