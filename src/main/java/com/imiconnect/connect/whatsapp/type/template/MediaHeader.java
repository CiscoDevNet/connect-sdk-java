package com.imiconnect.connect.whatsapp.type.template;

import lombok.Value;

import java.net.URI;

import static com.imiconnect.connect.core.util.Preconditions.notNullOrBlank;
import static java.util.Objects.requireNonNull;

/**
 * A specific type of substitution that only applies to a media header type on the whatsapp
 * template.
 */
@Value
public final class MediaHeader {

  enum Type {
    IMAGE,
    VIDEO,
    DOCUMENT
  }

  /** Type of media being displayed. */
  private final Type contentType;

  /** URL for media to be displayed */
  private final URI url;

  /** Filename to be used if media is saved to device */
  private final String filename;

  MediaHeader(Type contentType, URI url, String filename) {
    this.contentType = requireNonNull(contentType, "type can not be null.");
    this.url = requireNonNull(url, "url can not be null.");
    this.filename = notNullOrBlank(filename, filename);
  }

  public static MediaHeader ofImage(String url, String filename) {
    return new MediaHeader(Type.IMAGE, URI.create(url), filename);
  }

  public static MediaHeader ofVideo(String url, String filename) {
    return new MediaHeader(Type.VIDEO, URI.create(url), filename);
  }

  public static MediaHeader ofDocument(String url, String filename) {
    return new MediaHeader(Type.DOCUMENT, URI.create(url), filename);
  }
}
