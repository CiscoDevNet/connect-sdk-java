package com.imiconnect.connect.core.parser;

import com.imiconnect.connect.core.client.ConnectClient;
import com.imiconnect.connect.whatsapp.parser.WhatsAppMsgStatusDeserializer;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsgStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of the {@link ObjectParser} based on Jackson that will instantiate a single
 * instance of Jackson's ObjectMapper with a configuration that is used for all {@link ConnectClient}
 * types.
 */
public final class JacksonParser implements ObjectParser {

  // Jackson's object mapper is thread safe, we can use a single instance for all instantiations.
  private static final ObjectMapper MAPPER;

  static {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new ParameterNamesModule());
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);

    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(new StringWrapperSerializer());
    simpleModule.addDeserializer(WhatsAppMsgStatus.class, new WhatsAppMsgStatusDeserializer());
    objectMapper.registerModule(simpleModule);

    MAPPER = objectMapper;
  }

  public JacksonParser() {}

  @Override
  public byte[] writeValueAsBytes(Object o) {
    try {
      return MAPPER.writeValueAsBytes(o);
    } catch (JsonProcessingException e) {
      throw new ParseException("Error serializing object to json", e);
    }
  }

  @Override
  public <T> T readToObject(InputStream is, Class<T> clazz) {
    try {
      return MAPPER.readValue(is, clazz);
    } catch (IOException e) {
      throw new ParseException("Error deserializing json to " + clazz.getSimpleName(), e);
    }
  }
}
