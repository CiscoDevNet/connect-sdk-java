package com.imiconnect.connect.whatsapp.type;

import com.imiconnect.connect.core.parser.JacksonParser;
import com.imiconnect.connect.core.parser.ObjectParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.LocalDate;

import static com.imiconnect.connect.whatsapp.type.Contacts.ContactType.WORK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests (de)serialization of some of the content types by sending them through the JacksonParser to
 * test serialization, then attempts to deserialize the string back into an original object where
 * the original and deserialized are then compared for equality.
 */
//TODO: Expand on these tests and possibly come up with a better way.
public class SerializationTest {

  private ObjectParser parser = new JacksonParser();

  @Test
  public void shouldSerializeTextContent() {
    Text txt = Text.builder().content("textmessage").previewUrl(false).build();
    byte[] bytes = parser.writeValueAsBytes(txt);
    String txtStr = new String(bytes);
    System.out.println(txtStr);

    Text deserialized = parser.readToObject(new ByteArrayInputStream(bytes), Text.class);
    System.out.println(deserialized);
    assertThat(txt, is(deserialized));
  }

  @Test
  public void shouldSerializeAudioContent() {
    Audio audio = Audio.of("http://location.mp3");
    byte[] audiobytes = parser.writeValueAsBytes(audio);
    String audioStr = new String(audiobytes);
    System.out.println(audioStr);

    Audio deserialized = parser.readToObject(new ByteArrayInputStream(audiobytes), Audio.class);
    System.out.println(deserialized);
    assertThat(audio, is(deserialized));
  }

  @Test
  public void shouldSerializeContacts() {
    Contacts.Contact contact =
        Contacts.Contact.builder()
            .address(
                Contacts.Address.builder()
                    .city("San Jose")
                    .state("CA")
                    .street("300 E Tasman Dr")
                    .zip("95134")
                    .type(WORK)
                    .build())
            .email(Contacts.Email.builder().address("employee@cisco.com").type(WORK).build())
            .phone(
                Contacts.ContactNumber.builder()
                    .number("+15550001234")
                    .whatsAppId("id123")
                    .type(Contacts.ContactNumber.Type.WORK)
                    .build())
            .url(
                Contacts.Url.builder()
                    .address(URI.create("http://www.connect.com"))
                    .type(WORK)
                    .build())
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

    Contacts contactsMsg = Contacts.of(contact);

    byte[] bytes = parser.writeValueAsBytes(contactsMsg);
    String str = new String(bytes);
    System.out.println(str);

    Contacts deserialized = parser.readToObject(new ByteArrayInputStream(bytes), Contacts.class);
    System.out.println(deserialized);

    assertThat(contactsMsg, is(deserialized));
  }
}
