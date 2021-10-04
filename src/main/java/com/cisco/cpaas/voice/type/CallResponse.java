package com.cisco.cpaas.voice.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Response for sending a message or starting a session. */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class CallResponse {

  /** The status of the API request. */
  public enum Status {
    COMPLETED,
    QUEUED,
    FAILED
  }

  private final String sessionId;
  private final Status status;
}
