package com.cisco.cpaas.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Unit tests for the {@link MimeTypeDetector}. */
class MimeTypeDetectorTest {

  @ParameterizedTest
  @CsvSource({
    "aac, audio/x-aac",
    "mp3, audio/mpeg",
    "m4a, audio/mp4",
    "bmp, image/bmp",
    "gif, image/gif",
    "jpg, image/jpeg",
    "jpeg, image/jpeg",
    "png, image/png",
    "avi, video/x-msvideo",
    "mp4, video/mp4",
    "csv, text/csv",
    "txt, text/plain"
  })
  public void shouldDetectTypes(String extension, String expectedType) {
    String actualType = MimeTypeDetector.guessMimeType("file." + extension);
    assertThat(actualType, is(expectedType));
  }

  @Test
  public void shouldHandleUnknownType() {
    String actualType = MimeTypeDetector.guessMimeType("file.unknown");
    assertThat(actualType, is("application/octet-stream"));

  }

  @Test
  public void shouldHandleExtensionOnly() {
    String actualType = MimeTypeDetector.guessMimeType(".txt");
    assertThat(actualType, is("text/plain"));
  }
}
