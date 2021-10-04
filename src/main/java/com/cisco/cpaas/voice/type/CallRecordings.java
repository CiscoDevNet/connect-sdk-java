package com.cisco.cpaas.voice.type;

import lombok.Value;

import java.util.List;

/**
 * Definition for the API response that contains all the recordings associated with a particular
 * call session.
 */
@Value
public final class CallRecordings {

  private final String sessionId;
  private final List<Recording> recordings;

}
