package com.cisco.cpaas.core.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the {@link UrlUtils}. */
class UrlUtilsTest {

  @Test
  public void shouldRemoveTrailingSlash() {
    String normalizedUrl = UrlUtils.removeTrailingSlash("http://www.example.com/");
    assertThat(normalizedUrl, is("http://www.example.com"));
  }

  @Test
  public void shouldConvertMalformedUrlToIllegalArgument() {
    assertThrows(IllegalArgumentException.class, () -> UrlUtils.validateUrl("invalid url"));
  }

  @Test
  public void shouldThrowOnNullUrl() {
    assertThrows(NullPointerException.class, () -> UrlUtils.validateUrl(null));
  }

  @Test
  public void shouldConvertStringToUrl() {
    String url = "http://www.example.com";
    URL convertedUrl = UrlUtils.convertToURL(url);
    assertThat(convertedUrl.toString(), is(url));
  }
}
