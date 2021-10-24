package com.imiconnect.cpaas.voice.type;

import com.imiconnect.cpaas.core.client.ConnectResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object when sending a play and drop message. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PlayAndDropResponse extends ConnectResponse {

  private final String sessionId;
  private final CallState status;

  public PlayAndDropResponse(String requestId, String sessionId, CallState status) {
    super(requestId);
    this.sessionId = sessionId;
    this.status = status;
  }
}
