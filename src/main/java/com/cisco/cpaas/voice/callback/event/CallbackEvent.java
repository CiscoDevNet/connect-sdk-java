package com.cisco.cpaas.voice.callback.event;

import com.cisco.cpaas.voice.callback.action.Action;
import com.cisco.cpaas.voice.type.Call;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * Event that is published to the REST based webhook specified by the URL associated with a {@link
 * Call}. To continue a call flow, the response to this event should be one of the available {@link
 * Action}.
 */
@Getter
@ToString
@EqualsAndHashCode
abstract class CallbackEvent {

  /** Available callback event types. */
  enum Type {
    ACCEPTED,
    ANSWERED,
    PATCHED,
    RECORDED,
    COLLECTED_DIGITS,
    PLAYED,
    DROPPED,
    VOICEMAIL_DETECTED
  }

  /** The status of the event. */
  enum Status {
    SUCCESS,
    FAILURE
  }

  CallbackEvent(Instant eventTime, String sessionId, String transactionId, String correlationId) {
    this.eventTime = eventTime;
    this.sessionId = sessionId;
    this.transactionId = transactionId;
    this.correlationId = correlationId;
  }

  /** Time when the event was created. */
  private final Instant eventTime;

  /** Unique ID representing the call session. */
  private final String sessionId;

  /** Unique ID representing current transaction or menu. */
  private final String transactionId;

  /**
   * Arbitrary value provided on initial API invocation. This is echoed back to the event webhook
   * for every event in a given call.
   */
  private final String correlationId;
}
