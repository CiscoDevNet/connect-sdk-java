package com.cisco.cpaas.voice.callback.event;

import com.cisco.cpaas.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** A recording was taken. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RecordedEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.RECORDED;

  /** Name of the created recording. */
  private final String recordingFileName;

  /** Duration of the recording in seconds. */
  private final Integer recordingLength;

  /** If the event was successful or not. */
  private final Status status;

  /** Error information that will only be populated if the status is FAILURE. */
  private final ErrorResponse error;

  public RecordedEvent(Instant eventTime, String sessionId, String transactionId, String correlationId, String recordingFileName, Integer recordingLength, Status status, ErrorResponse error) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.recordingFileName = recordingFileName;
    this.recordingLength = recordingLength;
    this.status = status;
    this.error = error;
  }
}
