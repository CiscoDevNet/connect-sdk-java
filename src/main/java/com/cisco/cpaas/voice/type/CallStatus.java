package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.client.WebexResponse;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The status of a voice call. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CallStatus extends WebexResponse {

  private final String sessionId;
  private final PhoneNumber callerId;
  private final PhoneNumber dialedNumber;
  private final CallState status;
  private final String correlationId;
  private final Integer durationSeconds;
  private final Instant offeredTime;
  private final Instant answeredTime;
  private final ErrorResponse error;

  @lombok.Builder(builderClassName = "Builder")
  public CallStatus(
      String requestId,
      String sessionId,
      PhoneNumber callerId,
      PhoneNumber dialedNumber,
      CallState status,
      String correlationId,
      Integer durationSeconds,
      Instant offeredTime,
      Instant answeredTime,
      ErrorResponse error) {
    super(requestId);
    this.sessionId = sessionId;
    this.callerId = callerId;
    this.dialedNumber = dialedNumber;
    this.status = status;
    this.correlationId = correlationId;
    this.durationSeconds = durationSeconds;
    this.offeredTime = offeredTime;
    this.answeredTime = answeredTime;
    this.error = error;
  }
}
