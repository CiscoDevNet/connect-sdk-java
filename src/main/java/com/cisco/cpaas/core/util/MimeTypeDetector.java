package com.cisco.cpaas.core.util;

import jakarta.activation.FileTypeMap;
import jakarta.activation.MimetypesFileTypeMap;

import static java.util.Objects.requireNonNull;

/**
 * Utility to guess the MIME content type based on the filename. This implementation is based on the
 * {@link MimetypesFileTypeMap} that uses the <code>mime.types</code> file extension to mime type
 * mapping file located in the META-INF directory.
 */
// TODO: Test if the end user can override the file and supply their own mappings.
public class MimeTypeDetector {

  // TODO: This isn't available in java11.  See what import is needed or other solution.
  private static final FileTypeMap MIMETYPES_FILE_TYPE_MAP =
      MimetypesFileTypeMap.getDefaultFileTypeMap();

  /**
   * Guesses the MIME content type based on the supplied filename. It can be any string that ends
   * with a known extension type such as a url, directory path with filename etc. The restriction is
   * that it must contain the dot prior to the extension and the extension must be the last part of
   * the string. Some examples:
   *
   * <ul>
   *   <li>http://files.exampel.com/file.jpg</li>
   *   <li>/path/to/file/file.mp3</li>
   *   <li>file.txt</li>
   *   <li>.csv</li>
   * </ul>
   *
   * @param filename The text with file extension to be checked.
   * @return The mime type.
   */
  public static String guessMimeType(String filename) {
    requireNonNull(filename, "filename with extension can not be null.");
    return MIMETYPES_FILE_TYPE_MAP.getContentType(filename);
  }
}
