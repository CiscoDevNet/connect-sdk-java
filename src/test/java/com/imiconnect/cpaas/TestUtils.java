package com.imiconnect.cpaas;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Collection of utilities to facilitate some common test concerns. */
public class TestUtils {

  /** Gets a file from the test resources folder as a string based on the specified path. */
  public static String getFile(String fileLocation) {
    try {
      URL url = TestUtils.class.getResource(fileLocation);
      Path path = Paths.get(url.toURI());
      return new String(Files.readAllBytes(path), StandardCharsets.UTF_8.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
