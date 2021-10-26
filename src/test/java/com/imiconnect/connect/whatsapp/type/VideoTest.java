package com.imiconnect.connect.whatsapp.type;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link Video} type. */
class VideoTest {

  @Test
  public void shouldNotThrowWithValidMimeType() {
    new Video(URI.create("https://example.com/video.mp4"), "video/mp4", "caption");
  }

  @Test
  public void shouldNotAcceptInvalidMimeType() {
    assertThrows(IllegalArgumentException.class, () -> Video.of("https://example.com/video.avi"));
  }

  @Test
  public void shouldAutoDetectMimeType() {
    Video video = Video.of("https://example.com/video.mp4");
    assertThat(video.getMimeType(), equalTo("video/mp4"));
  }
}
