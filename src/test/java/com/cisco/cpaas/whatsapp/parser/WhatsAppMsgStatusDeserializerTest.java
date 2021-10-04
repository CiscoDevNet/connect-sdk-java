package com.cisco.cpaas.whatsapp.parser;

import com.cisco.cpaas.core.parser.JacksonParser;
import com.cisco.cpaas.core.parser.ObjectParser;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.whatsapp.type.Audio;
import com.cisco.cpaas.whatsapp.type.Content;
import com.cisco.cpaas.whatsapp.type.Text;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsgStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for the {@link WhatsAppMsgStatusDeserializer} that ensure it can properly deserialize a
 * response object while integrated as a jackson object mapper's submodule. This is done by loading
 * a json representation of the expected API response located in the test resources folder, then
 * attempting to deserialize it into the {@link WhatsAppMsgStatus} using the jackson version of the
 * {@link ObjectParser}.
 *
 * <p>The string values used in the test methods must match the text that is found in the json
 * files for the tests to be successful.
 */
class WhatsAppMsgStatusDeserializerTest {

  private ObjectParser parser;

  @BeforeEach
  public void init() {
    parser = new JacksonParser();
  }

  @ParameterizedTest
  @MethodSource("fileToContent")
  public void shouldDeserializeFullJsonOfDifferentContent(String fileName, Content content)
      throws Exception {
    WhatsAppMsgStatus actual = getActual(fileName);
    WhatsAppMsgStatus expected = getExpected(content).build();
    assertThat(actual, Matchers.is(expected));
  }

  private static Stream<Arguments> fileToContent() {
    return Stream.of(
        Arguments.of("status_full_audio.json", Audio.of("http://example.com/audio.mp3")),
        Arguments.of("status_full_text.json", Text.of("text message")));
  }

  @Test
  public void shouldDeserializeMinJson() throws Exception {
    WhatsAppMsgStatus actual = getActual("status_min_audio.json");
    WhatsAppMsgStatus expected =
        getExpected(Audio.of("http://example.com/audio.mp3"))
            .error(null)
            .correlationId(null)
            .build();
    assertThat(actual, Matchers.is(expected));
  }

  private WhatsAppMsgStatus getActual(String fileName) throws Exception {
    String json = getFile("/responses/whatsapp/" + fileName);
    return parser.readToObject(
        new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), WhatsAppMsgStatus.class);
  }

  private WhatsAppMsgStatus.Builder getExpected(Content content) {
    return WhatsAppMsgStatus.builder()
        .content(content)
        .error(new ErrorResponse("7000", "Message"))
        .correlationId("correlationId")
        .messageId("messageId")
        .acceptedTime(Instant.parse("2021-09-30T16:34:36.873Z"))
        .from("+15550001234")
        .to("+15559994321")
        .status(WhatsAppMsgStatus.Status.QUEUED)
        .statusTime(Instant.parse("2021-09-30T16:34:36.873Z"));
  }

  private String getFile(String fileLocation) throws Exception {
    URL url = this.getClass().getResource(fileLocation);
    Path path = Paths.get(url.toURI());
    return new String(Files.readAllBytes(path), StandardCharsets.UTF_8.toString());
  }
}
