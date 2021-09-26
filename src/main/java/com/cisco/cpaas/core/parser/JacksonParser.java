package com.cisco.cpaas.core.parser;

import com.cisco.cpaas.core.client.WebexClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of the {@link ObjectParser} based on Jackson that will instantiate a single instance of
 * Jackson's ObjectMapper with a configuration that is used for all {@link WebexClient} types.
 */
public class JacksonParser implements ObjectParser {

  // Jackson's object mapper is thread safe, we can use a single instance for all instantiations.
  private static final ObjectMapper MAPPER;
  static {
    ObjectMapper objectMapper = new ObjectMapper();
    //objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    MAPPER = objectMapper;
  }

  public JacksonParser() {}

  @Override
  public byte[] writeValueAsBytes(Object o) {
    try {
      return MAPPER.writeValueAsBytes(o);
    } catch (JsonProcessingException e) {
      throw new WebexParseException("Error serializing object to json", e);
    }
  }

  @Override
  public <T> T readToObject(InputStream is, Class<T> clazz) {
    try {
      return MAPPER.readValue(is, clazz);
    } catch (IOException e) {
      throw new WebexParseException("Error deserializing json to " + clazz.getSimpleName(), e);
    }
  }
}
