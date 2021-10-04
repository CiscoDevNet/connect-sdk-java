package com.cisco.cpaas.voice.callback.event;

import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.voice.type.TelephoneDigit;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Digits were collected by the user. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CollectedDigitsEvent extends CallbackEvent {

  private final CallbackEvent.Type event = CallbackEvent.Type.COLLECTED_DIGITS;

  /** Total number of DTMF digits pressed by the user. */
  private final Integer numOfDigits;

  /** All digits entered by the user. */
  private final String digitsReceived;

  /** The single digit that was pressed to terminate the call. */
  private final TelephoneDigit terminationDigit;

  /** Total duration of content played to the user. */
  private final Integer duration;

  /** If the event was successful or not. */
  private final Status status;

  /** Error information that will only be populated if the status is FAILURE. */
  private final ErrorResponse error;

  public CollectedDigitsEvent(Instant eventTime, String sessionId, String transactionId, String correlationId, Integer numOfDigits, String digitsReceived, TelephoneDigit terminationDigit, Integer duration, Status status, ErrorResponse error) {
    super(eventTime, sessionId, transactionId, correlationId);
    this.numOfDigits = numOfDigits;
    this.digitsReceived = digitsReceived;
    this.terminationDigit = terminationDigit;
    this.duration = duration;
    this.status = status;
    this.error = error;
  }
}
