package com.imiconnect.cpaas.voice.callback.event;

import com.imiconnect.cpaas.core.type.PhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The call was answered by a voicemail system. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class VoiceMailDetected extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.VOICEMAIL_DETECTED;

  /** Phone number of the calling party. */
  private final PhoneNumber callerId;

  /** Phone number of the called party. */
  private final PhoneNumber dialedNumber;

  public VoiceMailDetected(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      PhoneNumber callerId,
      PhoneNumber dialedNumber) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.callerId = callerId;
    this.dialedNumber = dialedNumber;
  }
}
