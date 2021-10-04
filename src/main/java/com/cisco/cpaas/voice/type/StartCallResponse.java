package com.cisco.cpaas.voice.type;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object for starting a new call session. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class StartCallResponse extends CallResponse {

  public StartCallResponse(String sessionId, Status status) {
    super(sessionId, status);
  }
}
