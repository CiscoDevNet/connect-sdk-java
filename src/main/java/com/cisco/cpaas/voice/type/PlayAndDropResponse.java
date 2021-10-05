package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.client.WebexResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/** Response object when sending a play and drop message. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PlayAndDropResponse extends WebexResponse {

  private final String sessionId;
  private final CallState status;

}
