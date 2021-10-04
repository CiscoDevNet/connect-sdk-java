package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.type.Idempotent;
import com.cisco.cpaas.core.type.MessageBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cisco.cpaas.core.util.Preconditions.isE164Endpoint;
import static com.cisco.cpaas.core.util.Preconditions.isEndpoint;
import static com.cisco.cpaas.core.util.Preconditions.validArgument;
import static com.cisco.cpaas.core.util.UnicodeDetector.containsUnicode;
import static com.cisco.cpaas.sms.type.SmsContentType.TEMPLATE;
import static com.cisco.cpaas.sms.type.SmsContentType.TEXT;
import static com.cisco.cpaas.sms.type.SmsContentType.UNICODE;
import static java.util.Objects.requireNonNull;

/** Request object used to send a new SMS message. */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmsMessage implements Idempotent {

  private final transient String idempotencyKey = UUID.randomUUID().toString();

  private final String from;
  private final String to;
  private final String content;
  private final SmsContentType contentType;
  private final Substitutions substitutions;
  private final String correlationId;
  private final String dtlTemplateId;
  private final String callbackUrl;
  private final String callbackData;
  private final Instant expireAt;

  /**
   * Starts building a new message that is based off of a template ID. The content type will
   * automatically be set to {@link SmsContentType#TEMPLATE}.
   *
   * @param templateId The message template to use.
   */
  public static MessageBuilder.From<Builder> fromTemplate(String templateId) {
    requireNonNull(templateId, "templateId can not be null.");
    return new Builder(templateId, TEMPLATE);
  }

  /**
   * Starts building a new message consisting of text or unicode content. This method will attempt
   * to determine if the string contains unicode literal characters and will set the content type to
   * either {@link SmsContentType#TEXT} or {@link SmsContentType#UNICODE} appropriately.
   *
   * <p>The unicode detection can be bypassed by using the overloaded factory method {@link
   * #of(String, SmsContentType)}. This may slightly improve performance or may be needed if
   * detection is not working as expected.
   *
   * @param content The content to send as the SMS message.
   */
  // TODO: Update docs for links to content detection etc.
  public static MessageBuilder.From<Builder> of(String content) {
    SmsContentType contentType = containsUnicode(content) ? UNICODE : TEXT;
    return SmsMessage.of(content, contentType);
  }

  /**
   * Starts building a new message of the specified content type.
   *
   * @param content the content to be used as the message.
   * @param type The type of message content. Typically this would be one of {@link
   *     SmsContentType#UNICODE}, {@link SmsContentType#TEXT}, or {@link SmsContentType#TEMPLATE}
   *     with this method.
   */
  public static MessageBuilder.From<Builder> of(String content, SmsContentType type) {
    if (content.length() > 1024) {
      throw new IllegalArgumentException("message content can not have more than 1024 characters.");
    }
    return new Builder(content, type);
  }

  /**
   * Starts building a new message consisting of binary content represented as a byte array. The
   * content type will automatically be set to {@link SmsContentType#BINARY}.
   *
   * @param content The binary data to send
   */
  public static MessageBuilder.From<Builder> of(byte[] content) {
    StringBuilder sb = new StringBuilder();
    for (byte b : content) {
      sb.append(Integer.toHexString(b));
    }
    return new Builder(sb.toString(), SmsContentType.BINARY);
  }

  /** Inner builder to construct a new {@link SmsMessage}. */
  public static final class Builder
      implements MessageBuilder.From<Builder>, MessageBuilder.To<Builder> {
    private String from;
    private String to;
    private String content;
    private SmsContentType contentType;
    private Map<String, String> substitutions;
    private String correlationId;
    private String dtlTemplateId;
    private String callbackUrl;
    private String callbackData;
    private Instant expireAt;

    private Builder(String content, SmsContentType contentType) {
      this.content = requireNonNull(content, "'content' is required.");
      this.contentType = requireNonNull(contentType, "contentType can not be null.");
    }

    @Override
    public MessageBuilder.To<Builder> from(String from) {
      this.from = from;
      return this;
    }

    @Override
    public Builder to(String to) {
      this.to = to;
      return this;
    }

    // TODO: We should look into adding a method to add multiple substitutions.
    public Builder withSubstitution(Substitution substitution) {
      if (substitutions == null) {
        this.substitutions = new HashMap<>();
      }
      this.substitutions.put(substitution.getKey(), substitution.getValue());
      return this;
    }

    public Builder withCorrelationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    public Builder withDtlTemplateId(String dtlTemplateId) {
      this.dtlTemplateId = dtlTemplateId;
      return this;
    }

    public Builder withCallbackUrl(String callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    public Builder withCallbackData(String callbackData) {
      this.callbackData = callbackData;
      return this;
    }

    public Builder withExpireAt(Instant expireAt) {
      this.expireAt = expireAt;
      return this;
    }

    public SmsMessage build() {
      validArgument(isEndpoint(from), "not a valid E.164 number");
      validArgument(isE164Endpoint(to), "not a valid E.164 number");
      Substitutions subs = substitutions == null ? null : new Substitutions(substitutions);
      return new SmsMessage(
          from,
          to,
          content,
          contentType,
          subs,
          correlationId,
          dtlTemplateId,
          callbackUrl,
          callbackData,
          expireAt);
    }
  }
}
