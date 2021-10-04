package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;

/** Defines whatsapp {@link Content} that represents an image file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Image extends MediaContent {

  private final WhatsAppContentType contentType = WhatsAppContentType.IMAGE;
  private final String caption;

  @lombok.Builder(builderClassName = "Builder")
  private Image(URI url, @Nullable String mimeType, @Nullable String caption) {
    super(url, mimeType);
    this.caption = caption;
  }

  /**
   * Convenience method to define new image content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the image can be found.
   * @return A new instance of {@link Image}.
   */
  public static Image of(String url) {
    return Image.of(URI.create(url));
  }

  /**
   * Convenience method to define new image content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the image can be found.
   * @return A new instance of {@link Image}.
   */
  public static Image of(URI url) {
    return new Image(url, null, null);
  }
}
