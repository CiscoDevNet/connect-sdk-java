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

/** Defines whatsapp {@link Content} that represents an audio file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Audio extends MediaContent {

  private static final Set<String> ALLOWED_MIME_TYPES =
      Collections.unmodifiableSet(
          new HashSet<>(
              Arrays.asList(
                  "audio/aac",
                  "audio/mp4",
                  "audio/amr",
                  "audio/mpeg",
                  "audio/ogg",
                  "audio/ogg; codecs=opus")));

  private final WhatsAppContentType contentType = WhatsAppContentType.AUDIO;

  public Audio(URI url, @Nullable String mimeType) {
    super(url, mimeType);
    validArgument(
        ALLOWED_MIME_TYPES.contains(this.getMimeType()),
        "Invalid MIME type " + this.getMimeType() + " for whatsapp audio.");
  }

  /**
   * Convenience method to define new audio content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the audio can be found.
   * @return A new instance of {@link Audio}.
   */
  public static Audio of(String url) {
    return new Audio(URI.create(url), null);
  }
}
