package com.cisco.cpaas.voice.callback.action;

import lombok.Builder;
import lombok.Value;

/** Callback response that tells the system to answer a call. */
@Value
@Builder(builderClassName = "Builder") // Included for consistency
public final class AnswerAction implements Action {

  private final Type action = Type.ANSWER;
}
