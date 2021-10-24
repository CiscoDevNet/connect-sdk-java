package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.imiconnect.connect.core.util.Preconditions.validArgument;

/** Defines whatsapp {@link Content} that represents a sticker image. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Sticker extends MediaContent {

  private static final Set<String> ALLOWED_MIME_TYPES =
      Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("image/webp")));

  private final WhatsAppContentType contentType = WhatsAppContentType.STICKER;

  @lombok.Builder(builderClassName = "Builder")
  private Sticker(URI url, @Nullable String mimeType) {
    super(url, mimeType);
    validArgument(
        ALLOWED_MIME_TYPES.contains(this.getMimeType()),
        "Invalid MIME type " + this.getMimeType() + " for whatsapp sticker.");
  }

  /**
   * Convenience method to define new sticker content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the sticker can be found.
   * @return A new instance of {@link Sticker}.
   */
  public static Sticker of(String url) {
    return Sticker.of(URI.create(url));
  }

  /**
   * Convenience method to define new sticker content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the sticker can be found.
   * @return A new instance of {@link Sticker}.
   */
  public static Sticker of(URI url) {
    return new Sticker(url, null);
  }
}
