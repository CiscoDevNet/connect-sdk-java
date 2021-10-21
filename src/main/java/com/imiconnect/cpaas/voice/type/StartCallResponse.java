package com.imiconnect.cpaas.voice.type;

import com.imiconnect.cpaas.core.client.WebexResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object for starting a new call session. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class StartCallResponse extends WebexResponse {

  private final String sessionId;
  private final CallState status;

  public StartCallResponse(String requestId, String sessionId, CallState status) {
    super(requestId);
    this.sessionId = sessionId;
    this.status = status;
  }
}
