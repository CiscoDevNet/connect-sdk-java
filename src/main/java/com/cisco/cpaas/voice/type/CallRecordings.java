package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.client.WebexResponse;
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
public final class CallRecordings extends WebexResponse {

  private final String sessionId;
  private final List<Recording> recordings;

}
