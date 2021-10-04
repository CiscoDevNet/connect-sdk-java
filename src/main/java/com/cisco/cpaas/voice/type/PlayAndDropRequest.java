package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.NonNull;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

/** Request for dialing a phone number, playing a message, and dropping the call. */
@Value
public class PlayAndDropRequest implements Call {

  private final String idempotencyKey = UUID.randomUUID().toString();
  @NonNull private final PhoneNumber callerId;
  @NonNull private final PhoneNumber dialedNumber;
  private final URI callbackUrl;
  private final String correlationId;
  @NonNull private final Audio audio;

  /** Inner builder for creating the play and drop request. */
  public static final class Builder implements CallSteps.To<Builder>, CallSteps.From<Builder> {

    private PhoneNumber callerId;
    private PhoneNumber dialedNumber;
    private URI callbackUrl;
    private String correlationId;
    private Audio audio;

    @Override
    public CallSteps.To<Builder> from(PhoneNumber callerId) {
      this.callerId = callerId;
      return this;
    }

    @Override
    public Builder to(PhoneNumber dialedNumber) {
      this.dialedNumber = dialedNumber;
      return this;
    }

    public Builder callbackUrl(URI callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    public Builder correlationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    public Builder audio(Audio audio) {
      this.audio = audio;
      return this;
    }

    public PlayAndDropRequest build() {
      return new PlayAndDropRequest(callerId, dialedNumber, callbackUrl, correlationId, audio);
    }
  }
}
