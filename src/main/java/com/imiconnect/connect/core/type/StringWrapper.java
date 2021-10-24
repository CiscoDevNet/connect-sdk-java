package com.imiconnect.connect.core.type;

/**
 * Common interface for wrapping a string as a specific type. Mostly this is used for serializing
 * all implementing types with the same (de)serializer.
 */
public interface StringWrapper {

  /**
   * Get the wrapped value.
   *
   * @return The Wrapped Value.
   */
  public String get();
}
