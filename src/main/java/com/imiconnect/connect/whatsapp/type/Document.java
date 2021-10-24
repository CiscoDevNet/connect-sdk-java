package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;

/** Defines whatsapp {@link Content} that represents a document file. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class Document extends MediaContent {

  private final WhatsAppContentType contentType = WhatsAppContentType.DOCUMENT;
  private final String caption;
  private final String fileName;

  @lombok.Builder(builderClassName = "Builder")
  private Document(
      URI url, @Nullable String mimeType, @Nullable String caption, @Nullable String fileName) {
    super(url, mimeType);
    this.caption = caption;
    this.fileName = fileName;
  }

  /**
   * Convenience method to define new document content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the document can be found.
   * @return A new instance of {@link Document}.
   */
  public static Document of(String url) {
    return Document.of(URI.create(url));
  }

  /**
   * Convenience method to define new document content at the given url. The mimeType will be
   * automatically detected. To manually set the mimeType, use the builder.
   *
   * @param url The URL where the document can be found.
   * @return A new instance of {@link Document}.
   */
  public static Document of(URI url) {
    return new Document(url, null, null, null);
  }
}
