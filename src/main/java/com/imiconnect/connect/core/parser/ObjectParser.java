package com.imiconnect.connect.core.parser;

import java.io.InputStream;

/**
 * Definition for an object parser that can (de)serialize domain objects.
 *
 * <p>Implementing classes should be thread safe.
 */
public interface ObjectParser {

  /**
   * Serialize the given object to a byte array.
   *
   * @param object The object to serialize.
   * @return The serialized byte array.
   * @throws ParseException when there is an issue serializing the object.
   */
  byte[] writeValueAsBytes(Object object);

  /**
   * Deserializes an {@link InputStream} into a specific type of object.
   *
   * @param is The input stream to deserialize.
   * @param clazz The type of object to attempt to deserialize into.
   * @param <T> The type of object to attempt to deserialize into.
   * @return The deserialized object.
   * @throws ParseException when there is an issue reading the input stream.
   */
  <T> T readToObject(InputStream is, Class<T> clazz);
}
