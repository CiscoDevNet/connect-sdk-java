package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.type.MessageBuilder;
import com.cisco.cpaas.core.type.PhoneNumber;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cisco.cpaas.core.util.Preconditions.notNullOrBlank;
import static com.cisco.cpaas.core.util.UnicodeDetector.containsUnicode;
import static com.cisco.cpaas.sms.type.SmsContentType.TEMPLATE;
import static com.cisco.cpaas.sms.type.SmsContentType.TEXT;
import static com.cisco.cpaas.sms.type.SmsContentType.UNICODE;
import static java.util.Objects.requireNonNull;

/** Request object used to send a new SMS message. */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmsMessageRequest implements SmsMessage {

  private final transient String idempotencyKey = UUID.randomUUID().toString();

  private final String from;
  private final PhoneNumber to;
  private final String content;
  private final SmsContentType contentType;
  private final Map<String, String> substitutions;
  private final String correlationId;
  private final String dltTemplateId;
  private final URI callbackUrl;
  private final String callbackData;

  /**
   * Starts building a new message that is based off of a template ID. The content type will
   * automatically be set to {@link SmsContentType#TEMPLATE}.
   *
   * @param templateId The message template to use.
   */
  public static MessageBuilder.From<Options> fromTemplate(String templateId) {
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
  public static MessageBuilder.From<Options> of(String content) {
    SmsContentType contentType = containsUnicode(content) ? UNICODE : TEXT;
    return SmsMessageRequest.of(content, contentType);
  }

  /**
   * Starts building a new message of the specified content type.
   *
   * @param content the content to be used as the message.
   * @param type The type of message content. Typically this would be one of {@link
   *     SmsContentType#UNICODE}, {@link SmsContentType#TEXT}, or {@link SmsContentType#TEMPLATE}
   *     with this method.
   */
  public static MessageBuilder.From<Options> of(String content, SmsContentType type) {
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
  public static MessageBuilder.From<Options> of(byte[] content) {
    StringBuilder sb = new StringBuilder();
    for (byte b : content) {
      sb.append(Integer.toHexString(b));
    }
    return new Builder(sb.toString(), SmsContentType.BINARY);
  }

  /** Inner builder to construct a new {@link SmsMessageRequest}. */
  public static final class Builder
      implements MessageBuilder.From<Options>, MessageBuilder.To<Options>, SmsMessage.Options {
    private String from;
    private PhoneNumber to;
    private String content;
    private SmsContentType contentType;
    private Map<String, String> substitutions;
    private String correlationId;
    private String dltTemplateId;
    private URI callbackUrl;
    private String callbackData;

    private Builder(String content, SmsContentType contentType) {
      this.content = requireNonNull(content, "'content' is required.");
      this.contentType = requireNonNull(contentType, "contentType can not be null.");
    }

    @Override
    public MessageBuilder.To<Options> from(String from) {
      this.from = from;
      return this;
    }

    @Override
    public Options to(String to) {
      this.to = PhoneNumber.of(to);
      return this;
    }

    /** Adds a single template substitution as a key value pair. A message can be templated by */
    public Options substitution(String key, String value) {
      if (substitutions == null) {
        this.substitutions = new HashMap<>();
      }
      this.substitutions.put(key, value);
      return this;
    }

    /**
     * Adds all substitutions in the map with any existing substitutions.
     *
     * @param substitutions The map of all desired substitutions.
     */
    public Options substitutions(Map<String, String> substitutions) {
      if (this.substitutions == null) {
        this.substitutions = substitutions;
      } else {
        this.substitutions.putAll(substitutions);
      }
      return this;
    }

    public Options correlationId(String correlationId) {
      this.correlationId = correlationId;
      return this;
    }

    @Override
    public Options dltTemplateId(String dltTemplateId) {
      this.dltTemplateId = dltTemplateId;
      return this;
    }

    @Override
    public Options callbackUrl(String callbackUrl) {
      this.callbackUrl = URI.create(callbackUrl);
      return this;
    }

    public Options callbackUrl(URI callbackUrl) {
      this.callbackUrl = callbackUrl;
      return this;
    }

    public Options callbackData(String callbackData) {
      this.callbackData = callbackData;
      return this;
    }

    public SmsMessageRequest build() {
      Map<String, String> subs =
          substitutions == null ? null : Collections.unmodifiableMap(substitutions);
      notNullOrBlank(from, "from");
      return new SmsMessageRequest(
          from,
          to,
          content,
          contentType,
          subs,
          correlationId,
          dltTemplateId,
          callbackUrl,
          callbackData);
    }
  }
}
