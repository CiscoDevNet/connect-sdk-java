package com.imiconnect.connect.voice.type;

import com.imiconnect.connect.core.annotation.Nullable;
import com.imiconnect.connect.core.type.Idempotent;
import com.imiconnect.connect.core.type.PhoneNumber;

import java.net.URI;

/** Voice call that will play a specified message and then drop the call. */
public interface PlayAndDrop extends Idempotent {

  PhoneNumber getCallerId();

  PhoneNumber getDialedNumber();

  @Nullable
  URI getCallbackUrl();

  @Nullable
  String getCorrelationId();

  Audio getAudio();

  /**
   * Creates a new play and drop voice message.
   *
   * @param callerId The number that represents the caller, as shown on a caller ID.
   * @return A builder to construct the rest of the message.
   */
  static CallSteps.To<PlayAndDropOptions> from(String callerId) {
    return new PlayAndDropRequest.Builder().from(callerId);
  }

  /** Defines the optional values that can be set on a play and drop call. */
  interface PlayAndDropOptions {

    /** URL for event callbacks that will provide the next actions for the call */
    PlayAndDropOptions callbackUrl(URI callbackUrl);

    /** URL for event callbacks that will provide the next actions for the call */
    PlayAndDropOptions callbackUrl(String callbackUrl);

    /**
     * A user-provided arbitrary string value that will be stored with the call status and sent in
     * all callback events.
     */
    PlayAndDropOptions correlationId(String correlationId);

    /**
     * An instance of one of the {@link Audio} objects that will be played before the call is
     * dropped.
     */
    PlayAndDropOptions audio(Audio audio);

    /** Creates the request. */
    PlayAndDropRequest build();
  }
}
