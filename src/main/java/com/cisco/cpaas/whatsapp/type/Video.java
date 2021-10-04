package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;

/** Defines whatsapp {@link Content} that represents a video file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Video extends MediaContent {

  private final WhatsAppContentType contentType = WhatsAppContentType.VIDEO;
  private final String caption;

  @lombok.Builder(builderClassName = "Builder")
  public Video(URI url, @Nullable String mimeType, @Nullable String caption) {
    super(url, mimeType);
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
