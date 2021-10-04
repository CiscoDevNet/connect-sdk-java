package com.cisco.cpaas.voice.type;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object when sending a play and drop message. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlayAndDropResponse extends CallResponse {

  public PlayAndDropResponse(String sessionId, Status status) {
    super(sessionId, status);
  }
}
