package com.cisco.cpaas.core.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link JacksonParser} to ensure it can (de)serialize basic map structures and
 * properly applies the necessary configurations such as timestamp formatting.
 */
class JacksonParserTest {

  private final String serializableJson = "{\"key\":{\"innerKey\":\"value\"}}";
  private JacksonParser parser;

  @BeforeEach
  public void init() {
    parser = new JacksonParser();
  }

  @Test
  public void shouldSerializeObject() {
    Map<String, Object> jsonMap = new HashMap<>();
    Map<String, Object> innerMap = new HashMap<>();
    innerMap.put("innerKey", "value");
    jsonMap.put("key", innerMap);

    byte[] bytes = parser.writeValueAsBytes(jsonMap);
    assertThat(new String(bytes), is(serializableJson));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldDeserializeObject() {
    InputStream is = new ByteArrayInputStream(serializableJson.getBytes(StandardCharsets.UTF_8));
    Map<String, Object> output = parser.readToObject(is, Map.class);
    assertThat(output.get("key"), instanceOf(Map.class));
    assertThat(((Map<String, String>) output.get("key")).get("innerKey"), is("value"));
  }

  @Test
  public void shouldSerializeInstantAsISO8601Format() {
    byte[] bytes = parser.writeValueAsBytes(Instant.ofEpochMilli(1630497600001L));
    assertThat(new String(bytes), is("\"2021-09-01T12:00:00.001Z\""));
  }

  @Test
  public void shouldThrowDomainExceptionOnError() {
    InputStream is = new ByteArrayInputStream("non json string".getBytes(StandardCharsets.UTF_8));
    assertThrows(WebexParseException.class, () -> parser.readToObject(is, Map.class));
  }

  @Test
  public void shouldNotSerializeNullValues() {
    Map<String, Object> mapWithNull = new HashMap<>();
    mapWithNull.put("Key", null);
    byte[] bytes = parser.writeValueAsBytes(mapWithNull);
    assertThat(new String(bytes), is("{}"));
  }
}
