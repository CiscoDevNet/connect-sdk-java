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

  /** Inner builder to construct a new {@link SmsMessageRequest}. */
  static final class Builder
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

    Builder(String content, SmsContentType contentType) {
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
