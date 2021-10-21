package com.imiconnect.cpaas.voice.callback.event;

import com.imiconnect.cpaas.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;
import java.util.List;

/** The call was patched together with another call. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PatchedEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.PATCHED;

  /** Time when the call was patched successfully. */
  private final Instant connectedOn;

  /** Contains the names of any recordings that resulted from the call patch. */
  private final List<String> recordingFileName;

  /** Specifies if the caller or callee dropped the call. */
  private final UserType droppedBy;

  /** If the event was successful or not. */
  private final Status status;

  /** Error information that will only be populated if the status is FAILURE. */
  private final ErrorResponse error;

  public PatchedEvent(
      Instant eventTime,
      String sessionId,
      String transactionId,
      String correlationId,
      Instant connectedOn,
      List<String> recordingFileName,
      UserType droppedBy,
      Status status,
      ErrorResponse error) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.connectedOn = connectedOn;
    this.recordingFileName = recordingFileName;
    this.droppedBy = droppedBy;
    this.status = status;
    this.error = error;
  }
}
