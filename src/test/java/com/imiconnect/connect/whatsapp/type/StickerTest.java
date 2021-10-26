package com.imiconnect.connect.whatsapp.type;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for {@link Sticker} type. */
class StickerTest {

  @Test
  public void shouldNotThrowWithValidMimeType() {
    new Sticker(URI.create("https://example.com/image.webp"), "image/webp");
  }

  @Test
  public void shouldNotAcceptInvalidMimeType() {
    assertThrows(IllegalArgumentException.class, () -> Sticker.of("https://example.com/image.bmp"));
  }

  @Test
  public void shouldAutoDetectMimeType() {
    Sticker sticker = Sticker.of("https://example.com/image.webp");
    assertThat(sticker.getMimeType(), equalTo("image/webp"));
  }
}
