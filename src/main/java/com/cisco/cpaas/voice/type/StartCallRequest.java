package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.annotation.Nullable;
import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/** Request object starting a new call session. */
@Value
final class StartCallRequest implements Call {

  private final String idempotencyKey = UUID.randomUUID().toString();
  private final PhoneNumber callerId;
  private final PhoneNumber dialedNumber;
  private final URI callbackUrl;
  private final String correlationId;
  private final Integer recordCallSeconds;
  private final Boolean detectVoiceMail;

  private StartCallRequest(
      PhoneNumber callerId,
      PhoneNumber dialedNumber,
      @Nullable URI callbackUrl,
      @Nullable String correlationId,
      @Nullable Integer recordCallSeconds,
      @Nullable Boolean detectVoiceMail) {
    this.callerId = requireNonNull(callerId, "callerId (from) can not be null.");
    this.dialedNumber = requireNonNull(dialedNumber, "dialedNumber (to) can not be null.");
    this.callbackUrl = callbackUrl;
    this.correlationId = correlationId;
    this.recordCallSeconds = recordCallSeconds;
    this.detectVoiceMail = detectVoiceMail;
  }

  public static CallSteps.From<CallOptions> builder() {
    return new Builder();
  }

  /** Inner builder for creating a new request. */
  static final class Builder
      implements CallSteps.From<CallOptions>, CallSteps.To<CallOptions>, CallOptions {

    private PhoneNumber callerId;
    private PhoneNumber dialedNumber;
    private URI callbackUrl;
    private String correlationId;
    private Integer recordCallSeconds;
    private Boolean detectVoiceMail;

    @Override
    public CallSteps.To<CallOptions> from(String callerId) {
      this.callerId = PhoneNumber.of(callerId);
      return this;
    }

    @Override
    public CallOptions to(String dialedNumber) {
      this.dialedNumber = PhoneNumber.of(dialedNumber);
      return this;
    }

    @Override
    public CallOptions callbackUrl(URI callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    @Override
    public CallOptions correlationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    @Override
    public CallOptions recordCallSeconds(Integer recordCallSeconds) {
      this.recordCallSeconds = recordCallSeconds;
      return this;
    }

    @Override
    public CallOptions detectVoiceMail(Boolean detectVoiceMail) {
      this.detectVoiceMail = detectVoiceMail;
      return this;
    }

    @Override
    public Call build() {
      return new StartCallRequest(
          callerId, dialedNumber, callbackUrl, correlationId, recordCallSeconds, detectVoiceMail);
    }
  }
}
