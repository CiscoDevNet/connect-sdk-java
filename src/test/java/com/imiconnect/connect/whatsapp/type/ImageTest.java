package com.imiconnect.connect.whatsapp.type;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link Image} type. */
class ImageTest {

  @Test
  public void shouldNotThrowWithValidMimeType() {
    new Image(URI.create("https://example.com/image.jpg"), "image/jpeg", null);
  }

  @Test
  public void shouldNotAcceptInvalidMimeType() {
    assertThrows(IllegalArgumentException.class, () -> Image.of("https://example.com/image.bmp"));
  }

  @Test
  public void shouldAutoDetectMimeType() {
    Image image = Image.of("https://example.com/image.jpg");
    assertThat(image.getMimeType(), equalTo("image/jpeg"));
  }
}
