package com.imiconnect.connect.voice.callback.event;

import com.imiconnect.connect.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Audio was played. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PlayedEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.PLAYED;

  /** Duration of the played audio in seconds. */
  private final Integer playedDuration;

  /** If the event was successful or not. */
  private final Status status;

  /** Error information that will only be populated if the status is FAILURE. */
  private final ErrorResponse error;

  public PlayedEvent(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      Integer playedDuration,
      Status status,
      ErrorResponse error) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.playedDuration = playedDuration;
    this.status = status;
    this.error = error;
  }
}
