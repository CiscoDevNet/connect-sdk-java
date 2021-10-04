package com.cisco.cpaas.voice.type;

import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/** Request object starting a new call session. */
@Value
@Builder(builderClassName = "Builder")
public final class StartCallRequest implements Call {

  private final String idempotencyKey = UUID.randomUUID().toString();
  @NonNull private final PhoneNumber callerId;
  @NonNull private final PhoneNumber dialedNumber;
  private final URI callbackUrl;
  private final String correlationId;
  private final Integer recordCallSeconds;
  private final Boolean detectVoiceMail;

  public CallSteps.To<Builder> from(PhoneNumber callerId) {
    return new Builder().from(callerId);
  }

  /** Inner builder for creating a new request. */
  public static final class Builder implements CallSteps.From<Builder>, CallSteps.To<Builder> {

    private PhoneNumber callerId;
    private PhoneNumber dialedNumber;
    private URI callbackUrl;
    private String correlationId;
    private Integer recordCallSeconds;
    private Boolean detectVoiceMail;

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

    public Builder recordCallSeconds(Integer recordCallSeconds) {
      this.recordCallSeconds = recordCallSeconds;
      return this;
    }

    public Builder detectVoiceMail(Boolean detectVoiceMail) {
      this.detectVoiceMail = detectVoiceMail;
      return this;
    }

    public StartCallRequest build() {
      requireNonNull(callerId, "callerId (from) can not be null.");
      requireNonNull(dialedNumber, "dialedNumber (to) can not be null.");
      return new StartCallRequest(
          callerId, dialedNumber, callbackUrl, correlationId, recordCallSeconds, detectVoiceMail);
    }
  }
}
