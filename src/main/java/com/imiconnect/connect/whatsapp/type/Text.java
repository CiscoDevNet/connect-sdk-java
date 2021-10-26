package com.imiconnect.connect.whatsapp.type;

import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;
import static java.util.Objects.requireNonNull;

/**
 * Defines whatsapp {@link Content} that represents a regular text message.
 *
 * <p>When setting the previewUrl property to <code>true</code>, the text message must contain a url
 * starting with <code>htto://</code> or <code>https://</code>.
 */
@Value
public final class Text implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.TEXT;
  private final String content;
  private final Boolean previewUrl;
  private final Map<String, String> substitutions;

  @lombok.Builder(builderClassName = "Builder")
  Text(
      String content,
      @Nullable Boolean previewUrl,
      @Singular @Nullable Map<String, String> substitutions) {
    this.content = requireNonNull(content, "text content can not be null.");
    this.previewUrl = previewUrl;
    this.substitutions = substitutions;

    if (previewUrl != null && previewUrl) {
      validArgument(
          content.contains("http://") || content.contains("https://"),
          "url must be present in message when preview url is true.");
    }
  }

  /** Create new text content. */
  public static Text of(String content) {
    return new Text(content, null, null);
  }
}
