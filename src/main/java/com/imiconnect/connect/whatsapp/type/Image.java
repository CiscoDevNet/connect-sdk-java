package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;

/** Defines whatsapp {@link Content} that represents an image file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Image extends MediaContent {

  private static final Set<String> ALLOWED_MIME_TYPES =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList("image/jpeg", "image/png")));

  private final WhatsAppContentType contentType = WhatsAppContentType.IMAGE;
  private final String caption;

  @lombok.Builder(builderClassName = "Builder")
  private Image(URI url, @Nullable String mimeType, @Nullable String caption) {
    super(url, mimeType);
    validArgument(
        ALLOWED_MIME_TYPES.contains(this.getMimeType()),
        "Invalid MIME type " + this.getMimeType() + " for whatsapp image.");
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
