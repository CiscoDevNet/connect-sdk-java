package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.type.Idempotent;
import com.cisco.cpaas.core.type.PhoneNumber;

import java.net.URI;

/** Definition for a call session. */
public interface Call extends Idempotent {

  public PhoneNumber getCallerId();

  public PhoneNumber getDialedNumber();

  public URI getCallbackUrl();

  public String getCorrelationId();

  /**
   * Creates a new request to start a call session.
   *
   * @param callerId the number that is dialing out.
   * @return The next step.
   */
  public static CallSteps.To<StartCallRequest.Builder> from(PhoneNumber callerId) {
    return new StartCallRequest.Builder().from(callerId);
  }

  /**
   * Creates a new play and drop voice message.
   *
   * @return A builder to construct the rest of the message.
   */
  public static CallSteps.From<PlayAndDropRequest.Builder> asMessage() {
    return new PlayAndDropRequest.Builder();
  }
}
