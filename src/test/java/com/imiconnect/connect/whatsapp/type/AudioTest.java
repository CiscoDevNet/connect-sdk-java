package com.imiconnect.connect.whatsapp.type;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link Audio} type. */
class AudioTest {

  @Test
  public void shouldNotThrowWithValidMimeType() {
    new Audio(URI.create("https://example.com/audio.mp3"), "audio/mpeg");
  }

  @Test
  public void shouldNotAcceptInvalidMimeType() {
    assertThrows(IllegalArgumentException.class, () -> Audio.of("https://example.com/audio.wav"));
  }

  @Test
  public void shouldAutoDetectMimeType() {
    Audio audio = Audio.of("https://example.com/audio.mp3");
    assertThat(audio.getMimeType(), equalTo("audio/mpeg"));
  }
}
