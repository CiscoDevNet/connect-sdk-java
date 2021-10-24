package com.imiconnect.connect.voice.type;

import com.imiconnect.connect.core.annotation.Nullable;
import com.imiconnect.connect.core.type.IdempotentRequest;
import com.imiconnect.connect.core.type.PhoneNumber;
import com.imiconnect.connect.voice.callback.action.PlayAction;

import java.net.URI;

/** Definition for a call session. */
public interface Call extends IdempotentRequest {

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
  static CallSteps.To<CallbackUrlStep> from(String callerId) {
    return new StartCallRequest.Builder().from(callerId);
  }

  /** Defines the optional values that can be set on a call session. */
  interface CallOptions {

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

  interface CallbackUrlStep {

    /** URL for event callbacks that will provide the next actions for the call */
    CallOptions callbackUrl(String url);

    /** URL for event callbacks that will provide the next actions for the call */
    CallOptions callbackUrl(URI url);
  }
}
