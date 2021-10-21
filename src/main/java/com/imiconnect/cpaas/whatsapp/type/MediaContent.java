package com.imiconnect.cpaas.whatsapp.type;

import com.imiconnect.cpaas.core.annotation.Nullable;
import com.imiconnect.cpaas.core.util.MimeTypeDetector;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Base implementation of WhatsApp {@link Content} that is the base for all media type content which
 * includes a url that points to the media resource and the mime type of the resource. If the mime
 * type is passed in as a null value, the type will be automatically populated based on the mapping
 * file found in the META-INF folder.
 */
@Getter
@ToString
@EqualsAndHashCode
abstract class MediaContent implements Content {

  private final URI url;
  private final String mimeType;

  protected MediaContent(URI url, @Nullable String mimeType) {
    this.url = requireNonNull(url, "media URL can not be null.");
    this.mimeType = nonNull(mimeType) ? mimeType : MimeTypeDetector.guessMimeType(url.toString());
  }
}
