package com.imiconnect.connect.sms.type;

import org.junit.jupiter.api.Test;

import static com.imiconnect.connect.sms.type.SmsContentType.UNICODE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for the construction and validation of an {@link SmsMessageRequest}. */
class SmsMessageRequestTest {

  private static final String PHONE_NUMBER = "+15551112222";

  @Test
  public void shouldSetTextTypeWhenNoUnicodePresent() {
    SmsMessage msg = SmsMessage.of("normal text").from(PHONE_NUMBER).to(PHONE_NUMBER).build();
    assertThat(msg.getContentType(), is(SmsContentType.TEXT));
  }

  @Test
  public void shouldSetUnicodeTypeWhenUnicodePresent() {
    String input = "text \uD801";
    SmsMessage msg = SmsMessage.of(input).from(PHONE_NUMBER).to(PHONE_NUMBER).build();
    assertThat(msg.getContentType(), is(UNICODE));
  }

  @Test
  public void shouldSetTemplateTypeWhenUsingFromTemplate() {
    SmsMessage msg = SmsMessage.fromTemplate("id").from(PHONE_NUMBER).to(PHONE_NUMBER).build();
    assertThat(msg.getContentType(), is(SmsContentType.TEMPLATE));
  }

  @Test
  public void shouldCreateBinaryMessageWhenUsingByteArray() {
    byte b0 = 0b0100;
    byte b1 = 0b0111;
    byte b2 = 0b0101;
    byte b3 = 0b1010;
    byte b4 = 0b1001;
    byte b5 = 0b1101;
    byte b6 = 0b0110;
    byte b7 = 0b1011;
    byte[] bytes = new byte[] {b0, b1, b2, b3, b4, b5, b6, b7};
    String binaryAsHex = "475a9d6b";
    SmsMessage msg = SmsMessage.of(bytes).from(PHONE_NUMBER).to(PHONE_NUMBER).build();
    assertThat(msg.getContentType(), is(SmsContentType.BINARY));
    assertThat(msg.getContent(), is(binaryAsHex));
  }

  @Test
  public void shouldNotAllowTextContentWithMoreThan1024Chars() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1025; i++) {
      sb.append("a");
    }
    assertThrows(
        IllegalArgumentException.class,
        () -> SmsMessage.of(sb.toString()).from(PHONE_NUMBER).to(PHONE_NUMBER).build());
  }

}
