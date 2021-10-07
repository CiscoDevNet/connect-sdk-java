package com.cisco.cpaas.whatsapp.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/** Unit tests for the {@link WhatsAppContentType}. */
class WhatsAppContentTypeTest {

  @ParameterizedTest
  @MethodSource("expectedTypes")
  public void shouldReturnClassType(String enumName, Class<?> type) {
    assertThat(WhatsAppContentType.getType(enumName), equalTo(type));
  }

  private static Stream<Arguments> expectedTypes() {
    return Stream.of(
        Arguments.of("AUDIO", Audio.class),
        Arguments.of("audio", Audio.class),
        Arguments.of("CONTACTS", Contacts.class),
        Arguments.of("contacts", Contacts.class),
        Arguments.of("DOCUMENT", Document.class),
        Arguments.of("document", Document.class),
        Arguments.of("IMAGE", Image.class),
        Arguments.of("image", Image.class),
        Arguments.of("LOCATION", Location.class),
        Arguments.of("location", Location.class),
        Arguments.of("STICKER", Sticker.class),
        Arguments.of("sticker", Sticker.class),
        Arguments.of("TEXT", Text.class),
        Arguments.of("text", Text.class),
        Arguments.of("TEMPLATE", TemplateContent.class),
        Arguments.of("template", TemplateContent.class),
        Arguments.of("VIDEO", Video.class),
        Arguments.of("video", Video.class));
  }
}
