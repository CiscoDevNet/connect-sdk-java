package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.Idempotent;
import com.cisco.cpaas.core.type.PhoneNumber;
import com.cisco.cpaas.voice.callback.action.PlayAction;

import java.net.URI;

/** Definition for a call session. */
public interface Call extends Idempotent {

  PhoneNumber getCallerId();

  PhoneNumber getDialedNumber();

  @Nullable
  URI getCallbackUrl();

  @Nullable
  String getCorrelationId();

  /**
   * Creates a new request to start a call session.
   *
   * @param callerId the number that is dialing out.
   * @return The next step.
   */
  static CallSteps.To<CallOptions> from(String callerId) {
    return new StartCallRequest.Builder().from(callerId);
  }

  /** Defines the optional values that can be set on a call session. */
  interface CallOptions {

    /** URL for event callbacks that will provide the next actions for the call */
    CallOptions callbackUrl(URI callbackUrl);

    /**
     * A user-provided arbitrary string value that will be stored with the call status and sent in
     * all callback events.
     */
    CallOptions correlationId(String correlationId);

    /**
     * If present and a positive value, record the call for this many seconds. A value of 0 means to
     * record until the end of the call.
     */
    CallOptions recordCallSeconds(Integer recordCallSeconds);

    /**
     * if true, VoiceMailDetected event will be sent if call is answered by an Answering Machine. A
     * {@link PlayAction} is expected in response of this event.
     */
    CallOptions detectVoiceMail(Boolean detectVoiceMail);

    /** Creates the call request. */
    Call build();
  }
}
