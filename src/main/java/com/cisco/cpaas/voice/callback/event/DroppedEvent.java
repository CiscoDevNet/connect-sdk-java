package com.cisco.cpaas.voice.callback.event;

import com.cisco.cpaas.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The call was dropped. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DroppedEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.DROPPED;

  /** Specifies if the caller or callee dropped the call. */
  private final UserType droppedBy;

  /** If the event was successful or not. */
  private final Status status;

  /** Error information that will only be populated if the status is FAILURE. */
  private final ErrorResponse error;

  public DroppedEvent(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      UserType droppedBy,
      Status status,
      ErrorResponse error) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.droppedBy = droppedBy;
    this.status = status;
    this.error = error;
  }
}
