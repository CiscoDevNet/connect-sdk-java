package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.annotation.Nullable;
import lombok.Builder;
import lombok.Value;

import static com.cisco.cpaas.core.util.Preconditions.validArgument;
import static java.util.Objects.requireNonNull;

/**
 * Defines whatsapp {@link Content} that represents a regular text message.
 *
 * <p>When setting the previewUrl property to <code>true</code>, the text message must contain a url
 * starting with <code>htto://</code> or <code>https://</code>.
 */
@Value
@Builder(builderClassName = "Builder")
public final class Text implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.TEXT;
  // TODO: Find out why having the name content causes JsonUnwrapped to not work on status obj.
  private final String content;
  private final Boolean previewUrl;

  public Text(String content, @Nullable Boolean previewUrl) {
    this.content = requireNonNull(content, "text content can not be null.");
    this.previewUrl = previewUrl;
    if (previewUrl != null && previewUrl) {
      validArgument(
          content.contains("http://") || content.contains("https://"),
          "url must be present in message when preview url is true.");
    }
  }

  /** Create new text content. */
  public static Text of(String content) {
    return new Text(content, null);
  }
}
