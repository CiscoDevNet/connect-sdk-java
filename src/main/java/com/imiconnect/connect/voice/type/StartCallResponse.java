package com.imiconnect.connect.voice.type;

import com.imiconnect.connect.core.client.ConnectResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object for starting a new call session. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class StartCallResponse extends ConnectResponse {

  private final String sessionId;
  private final CallState status;

  public StartCallResponse(String requestId, String sessionId, CallState status) {
    super(requestId);
    this.sessionId = sessionId;
    this.status = status;
  }
}
