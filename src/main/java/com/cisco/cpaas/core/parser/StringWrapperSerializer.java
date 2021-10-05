package com.cisco.cpaas.core.parser;

import com.cisco.cpaas.core.type.StringWrapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Jackson serializer to unwrap the {@link StringWrapper} as a normal string to be included with the
 * requests.
 */
class StringWrapperSerializer extends StdSerializer<StringWrapper> {

  protected StringWrapperSerializer() {
    super(StringWrapper.class);
  }

  @Override
  public void serialize(StringWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.get());
  }
}
