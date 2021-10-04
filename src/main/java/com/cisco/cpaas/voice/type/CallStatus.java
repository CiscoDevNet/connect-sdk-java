package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.Value;

import java.time.Instant;

/** The status of a voice call. */
@Value
public final class CallStatus {

  private final String sessionId;
  private final PhoneNumber callerId;
  private final PhoneNumber dialedNumber;
  private final CallResponse.Status status;
  private final String correlationId;
  private final Integer durationSeconds;
  private final Instant offeredTime;
  private final Instant answeredTime;
}
