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

}
