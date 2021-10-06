package com.cisco.cpaas.voice.callback.event;

import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The call was answered. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AnsweredEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.ANSWERED;

  /** Phone number of the calling party. */
  private final PhoneNumber callerId;

  /** Phone number of the called party. */
  private final PhoneNumber dialedNumber;

  /** Time when the call was placed. */
  private final Instant offeredTime;

  /** Time when the call was answered. */
  private final Instant answeredTime;

  public AnsweredEvent(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      PhoneNumber callerId,
      PhoneNumber dialedNumber,
      Instant offeredTime,
      Instant answeredTime) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.callerId = callerId;
    this.dialedNumber = dialedNumber;
    this.offeredTime = offeredTime;
    this.answeredTime = answeredTime;
  }
}
