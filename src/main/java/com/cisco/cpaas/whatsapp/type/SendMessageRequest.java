package com.cisco.cpaas.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

/** Default implementation of the {@link WhatsAppMsg}. */
@Value
public final class SendMessageRequest implements WhatsAppMsg {

  // TODO: Make from and to into entities.
  private final String idempotencyKey = UUID.randomUUID().toString();
  private final String from;
  private final String to;
  private final URI callbackUrl;
  private final String callbackData;
  private final String correlationId;
  private final Substitutions substitutions;
  @JsonUnwrapped private final Content content;

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Content> T getCastContent() {
    return (T) content;
  }

  /**
   * Step builder to create new instances of a {@link WhatsAppMsg}. The builder will require that
   * the content be populated first, then the from and to fields. The other fields are all optional.
   */
  protected static final class Builder
      implements MessageSteps.From,
          MessageSteps.To,
          MessageSteps.Options,
          MessageSteps.ContentCreator {

    private String from;
    private String to;
    private URI callbackUrl;
    private String callbackData;
    private String correlationId;
    private Substitutions substitutions;
    private Content content;

    @Override
    public MessageSteps.From content(Content content) {
      this.content = content;
      return this;
    }

    @Override
    public MessageSteps.To from(String from) {
      this.from = from;
      return this;
    }

    @Override
    public MessageSteps.Options to(String to) {
      this.to = to;
      return this;
    }

    @Override
    public MessageSteps.Options callbackUrl(String callbackUrl) {
      this.callbackUrl = URI.create(callbackUrl);
      return this;
    }

    @Override
    public MessageSteps.Options callbackUrl(URI callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    @Override
    public MessageSteps.Options callbackData(String callbackData) {
      this.callbackData = callbackData;
      return this;
    }

    @Override
    public MessageSteps.Options correlationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    @Override
    public MessageSteps.Options substitutions(Substitutions substitutions) {
      this.substitutions = substitutions;
      return this;
    }

    @Override
    public SendMessageRequest build() {
      return new SendMessageRequest(
          from, to, callbackUrl, callbackData, correlationId, substitutions, content);
    }
  }
}
