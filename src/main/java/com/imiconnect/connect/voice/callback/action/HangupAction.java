package com.imiconnect.connect.voice.callback.action;

import lombok.Builder;
import lombok.Value;

/** Hangs up a call session. */
@Value
@Builder(builderClassName = "Builder")
public class HangupAction implements Action {

  private final Type action = Type.HANGUP;

  /** Optional reason that is logged for hanging up the call. */
  private final String reason;

  public HangupAction() {
    this.reason = null;
  }

  public HangupAction(String reason) {
    this.reason = reason;
  }
}
