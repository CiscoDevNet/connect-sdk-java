package com.imiconnect.cpaas.whatsapp.type;

import com.imiconnect.cpaas.core.client.ConnectResponse;
import com.imiconnect.cpaas.whatsapp.WhatsAppClient;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Response to the {@link WhatsAppClient#sendMessage(WhatsAppMsg)}. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class WhatsAppSendMsgResponse extends ConnectResponse {

  private final String messageId;
  private final String correlationId;
  private final Instant acceptedTime;

  public WhatsAppSendMsgResponse(
      String requestId, String messageId, String correlationId, Instant acceptedTime) {
    super(requestId);
    this.messageId = messageId;
    this.correlationId = correlationId;
    this.acceptedTime = acceptedTime;
  }
}
