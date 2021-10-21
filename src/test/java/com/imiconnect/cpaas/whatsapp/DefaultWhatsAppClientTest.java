package com.imiconnect.cpaas.whatsapp;

import com.imiconnect.cpaas.core.client.InternalClient;
import com.imiconnect.cpaas.whatsapp.type.WhatsAppMsg;
import com.imiconnect.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.imiconnect.cpaas.whatsapp.type.WhatsAppSendMsgResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests to ensure the {@link DefaultWhatsAppClient} adapts the interface to the common
 * internal client.
 */
@ExtendWith(MockitoExtension.class)
class DefaultWhatsAppClientTest {

  @Mock private InternalClient internalClient;

  private DefaultWhatsAppClient client;

  @BeforeEach
  public void init() {
    client = new DefaultWhatsAppClient(internalClient);
  }

  @Test
  public void shouldSendMessage() {
    WhatsAppSendMsgResponse expected =
        new WhatsAppSendMsgResponse(null, "messageId", "correlationId", Instant.now());
    when(internalClient.post(anyString(), any(), any())).thenReturn(expected);

    WhatsAppMsg msg =
        WhatsAppMsg.ofText("text message").from("+15550001234").to("+15559994321").build();
    WhatsAppSendMsgResponse actual = client.sendMessage(msg);

    Mockito.verify(internalClient)
        .post("/v1/whatsapp/messages", msg, WhatsAppSendMsgResponse.class);
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void shouldGetStatus() {
    WhatsAppMsgStatus expected = WhatsAppMsgStatus.builder().build();
    when(internalClient.get("/v1/whatsapp/messages/messageId", WhatsAppMsgStatus.class))
        .thenReturn(expected);
    WhatsAppMsgStatus actual = client.getStatus("messageId");
    assertThat(expected, is(actual));
  }
}
