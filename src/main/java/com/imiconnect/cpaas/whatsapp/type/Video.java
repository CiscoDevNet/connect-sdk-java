package com.imiconnect.cpaas.whatsapp.type;

import com.imiconnect.cpaas.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.imiconnect.cpaas.core.util.Preconditions.validArgument;

/** Defines whatsapp {@link Content} that represents a video file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Video extends MediaContent {

  private static final Set<String> ALLOWED_MIME_TYPES =
    Collections.unmodifiableSet(new HashSet<>(Arrays.asList("video/mp4", "video/3gpp")));

  private final WhatsAppContentType contentType = WhatsAppContentType.VIDEO;
  private final String caption;

  @lombok.Builder(builderClassName = "Builder")
  public Video(URI url, @Nullable String mimeType, @Nullable String caption) {
    super(url, mimeType);
    validArgument(
      ALLOWED_MIME_TYPES.contains(this.getMimeType()),
      "Invalid MIME type " + this.getMimeType() + " for whatsapp video.");
    this.caption = caption;
  }

  /**
   * Convenience method to define new video content at the given url.
   *
   * @param url The URL where the video can be found.
   * @return A new instance of {@link Video}.
   */
  public static Video of(String url) {
    return Video.of(URI.create(url));
  }

  /**
   * Convenience method to define new video content at the given url.
   *
   * @param url The URL where the video can be found.
   * @return A new instance of {@link Video}.
   */
  public static Video of(URI url) {
    return new Video(url, null, null);
  }
}
