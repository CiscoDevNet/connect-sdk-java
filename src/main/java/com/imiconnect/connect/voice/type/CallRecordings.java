package com.imiconnect.connect.voice.type;

import com.imiconnect.connect.core.client.ConnectResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Definition for the API response that contains all the recordings associated with a particular
 * call session.
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CallRecordings extends ConnectResponse {

  private final String sessionId;
  private final List<Recording> recordings;

  public CallRecordings(String requestId, String sessionId, List<Recording> recordings) {
    super(requestId);
    this.sessionId = sessionId;
    this.recordings = recordings;
  }
}
