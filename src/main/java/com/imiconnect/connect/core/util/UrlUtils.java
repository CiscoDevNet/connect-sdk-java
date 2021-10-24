package com.imiconnect.connect.core.util;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

/** Collection of utility methods for working with a URL. */
public class UrlUtils {

  /**
   * Normalizes a string representation of a URL by checking if a trailing <code>/</code> was added
   * and removing it if it exists.
   *
   * @param url the URL to normalize.
   * @return the normalized URL.
   */
  public static String removeTrailingSlash(String url) {
    requireNonNull(url, "url can not be null");
    if (url.endsWith("/")) {
      return url.substring(0, url.length() - 1);
    }
    return url;
  }

  /**
   * Validates a string representation of a URL using the {@link URL} type. This is a convenience
   * method to wrap the {@link MalformedURLException} as an {@link IllegalArgumentException}.
   *
   * @param url the string representation of a url.
   * @return the original url.
   */
  public static String validateUrl(String url) {
    convertToURL(url);
    return url;
  }

  /**
   * Takes a string representation of a URL and converts it to a {@link URL} after calling {@link
   * #removeTrailingSlash(String)}. If the URL is malformed, the {@link MalformedURLException} will
   * be wrapped as an {@link IllegalArgumentException}.
   *
   * @param url the string representation of the URL.
   * @return the URL.
   */
  public static URL convertToURL(String url) {
    requireNonNull(url, "url can not be null");
    try {
      return new URL(removeTrailingSlash(url));
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Invalid string representation of URL: " + url, e);
    }
  }
}
