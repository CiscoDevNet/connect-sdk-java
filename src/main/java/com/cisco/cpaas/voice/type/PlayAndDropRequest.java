package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/** Request for dialing a phone number, playing a message, and dropping the call. */
@Value
class PlayAndDropRequest implements PlayAndDrop {

  private final String idempotencyKey = UUID.randomUUID().toString();
  private final PhoneNumber callerId;
  private final PhoneNumber dialedNumber;
  private final URI callbackUrl;
  private final String correlationId;
  private final Audio audio;

  private PlayAndDropRequest(
      PhoneNumber callerId,
      PhoneNumber dialedNumber,
      @Nullable URI callbackUrl,
      @Nullable String correlationId,
      Audio audio) {
    this.callerId = requireNonNull(callerId, "callerId (from) can not be null.");
    this.dialedNumber = requireNonNull(dialedNumber, "dialedNumber (to) can not be null.");
    this.callbackUrl = callbackUrl;
    this.correlationId = correlationId;
    this.audio = requireNonNull(audio, "audio message can not be null");
  }

  public static CallSteps.From<PlayAndDropOptions> builder() {
    return new Builder();
  }

  /** Inner builder for creating the play and drop request. */
  static final class Builder
      implements CallSteps.To<PlayAndDropOptions>,
          CallSteps.From<PlayAndDropOptions>,
          PlayAndDropOptions {

    private PhoneNumber callerId;
    private PhoneNumber dialedNumber;
    private URI callbackUrl;
    private String correlationId;
    private Audio audio;

    @Override
    public CallSteps.To<PlayAndDropOptions> from(String callerId) {
      this.callerId = PhoneNumber.of(callerId);
      return this;
    }

    @Override
    public PlayAndDropOptions to(String dialedNumber) {
      this.dialedNumber = PhoneNumber.of(dialedNumber);
      return this;
    }

    @Override
    public PlayAndDropOptions callbackUrl(URI callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    @Override
    public PlayAndDropOptions correlationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    @Override
    public PlayAndDropOptions audio(Audio audio) {
      this.audio = audio;
      return this;
    }

    @Override
    public PlayAndDropRequest build() {
      return new PlayAndDropRequest(callerId, dialedNumber, callbackUrl, correlationId, audio);
    }
  }
}
