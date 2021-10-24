package com.imiconnect.cpaas.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.imiconnect.cpaas.core.type.PhoneNumber;
import lombok.Value;

import java.net.URI;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/** Default implementation of the {@link WhatsAppMsg}. */
@Value
public final class WhatsAppSendMsgRequest implements WhatsAppMsg {

  private final String idempotencyKey = UUID.randomUUID().toString();
  private final String from;
  private final PhoneNumber to;
  private final URI callbackUrl;
  private final String callbackData;
  private final String correlationId;
  @JsonUnwrapped private final Content content;

  WhatsAppSendMsgRequest(
      String from,
      PhoneNumber to,
      URI callbackUrl,
      String callbackData,
      String correlationId,
      Content content) {
    this.from = requireNonNull(from, "from number can not be null.");
    this.to = requireNonNull(to, "to number can not be null.");
    this.callbackUrl = callbackUrl;
    this.callbackData = callbackData;
    this.correlationId = correlationId;
    this.content = requireNonNull(content, "content can not be null.");
  }

  @JsonIgnore
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
    private PhoneNumber to;
    private URI callbackUrl;
    private String callbackData;
    private String correlationId;
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
      this.to = PhoneNumber.of(to);
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
    public WhatsAppSendMsgRequest build() {
      return new WhatsAppSendMsgRequest(
          from, to, callbackUrl, callbackData, correlationId, content);
    }
  }
}
