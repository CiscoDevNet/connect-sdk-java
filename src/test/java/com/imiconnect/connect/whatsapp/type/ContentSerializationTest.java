package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.parser.JacksonParser;
import com.imiconnect.connect.core.parser.ObjectParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests (de)serialization of some of the content types by sending them through the JacksonParser to
 * test serialization, then attempts to deserialize the string back into an original object where
 * the original and deserialized are then compared for equality.
 */
// TODO: Expand on these tests and possibly come up with a better way.
public class ContentSerializationTest {

  private ObjectParser parser;

  @BeforeEach
  public void init() {
    parser = new JacksonParser();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("content")
  public void shouldSerializeThenDeserializeContent(Content expected) {
    byte[] bytes = parser.writeValueAsBytes(expected);

    System.out.println(new String(bytes));

    Content deserialized = parser.readToObject(new ByteArrayInputStream(bytes), Content.class);
    System.out.println(deserialized);
    assertThat(deserialized, is(expected));
  }

  private static Stream<Arguments> content() {
    return Stream.of(
        Arguments.of(textContent()),
        Arguments.of(getAudioContent()),
        Arguments.of(getContactContent()));
  }

  private static Text textContent() {
    return Text.builder().content("textmessage").previewUrl(false).build();
  }

  private static Audio getAudioContent() {
    return Audio.of("http://example.com/audio.mp3");
  }

  private static Contacts getContactContent() {
    Contact contact =
        Contact.builder()
            .address(
                Address.asWork()
                    .city("San Jose")
                    .state("CA")
                    .street("300 E Tasman Dr")
                    .zip("95134")
                    .countryCode("US")
                    .country("USA")
                    .build())
            .email(Email.ofWorkEmail("employee@cisco.com"))
            .phone(ContactNumber.ofPhoneNumber("+15550001234", ContactNumber.Type.WORK))
            .phone(ContactNumber.ofWhatsAppId("abc123", ContactNumber.Type.IPHONE))
            .url(ContactUrl.ofWorkUrl("http://www.connect.com"))
            .formattedName("Mr. John Connect Doe Jr.")
            .firstName("John")
            .lastName("Doe")
            .middleName("Connect")
            .namePrefix("Mr.")
            .nameSuffix("Jr.")
            .birthday(LocalDate.now())
            .company("Cisco Systems")
            .department("Engineering")
            .title("Someone")
            .build();

    Contacts contactsContent = Contacts.of(contact);
    return contactsContent;
  }
}
