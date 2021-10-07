package com.cisco.cpaas.sms;

import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.sms.type.SendSmsResponse;
import com.cisco.cpaas.sms.type.SmsMessageRequest;
import com.cisco.cpaas.sms.type.SmsMessageStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Unit tests for the {@link DefaultSmsClient}. */
@ExtendWith(MockitoExtension.class)
class DefaultSmsClientTest {

  @Mock private InternalClient internalClient;

  private DefaultSmsClient client;

  @BeforeEach
  public void init() {
    client = new DefaultSmsClient(internalClient);
  }

  @Test
  public void shouldSendMessage() {
    SendSmsResponse expectedResponse =
        new SendSmsResponse("requestId", Instant.now(), "messageId", "correlationId");
    when(internalClient.post(anyString(), any(), any())).thenReturn(expectedResponse);

    SmsMessageRequest msg = SmsMessageRequest.of("text message").from("+15550001234").to("+15559994321").build();
    SendSmsResponse actualResponse = client.sendMessage(msg);

    verify(internalClient).post("/v1/sms/messages", msg, SendSmsResponse.class);
    assertThat(expectedResponse, equalTo(actualResponse));
  }

  @Test
  public void shouldGetStatus() {
    SmsMessageStatus expected = SmsMessageStatus.builder().build();
    when(internalClient.get("/v1/sms/messages/messageId", SmsMessageStatus.class))
        .thenReturn(expected);

    SmsMessageStatus actual = client.getStatus("messageId");
    assertThat(expected, equalTo(actual));
  }
}
