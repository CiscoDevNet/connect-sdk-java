package com.imiconnect.connect.voice.callback.event;

import com.imiconnect.connect.core.type.PhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The call was accepted. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AcceptedEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.ACCEPTED;

  /** Phone number of the calling party. */
  private final PhoneNumber callerId;

  /** Phone number of the called party. */
  private final PhoneNumber dialedNumber;

  /** Time when the call was placed. */
  private final Instant offeredTime;

  public AcceptedEvent(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      PhoneNumber callerId,
      PhoneNumber dialedNumber,
      Instant offeredTime) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.callerId = callerId;
    this.dialedNumber = dialedNumber;
    this.offeredTime = offeredTime;
  }
}
