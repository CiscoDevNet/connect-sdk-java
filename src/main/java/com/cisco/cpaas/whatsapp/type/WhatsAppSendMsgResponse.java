package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.client.WebexResponse;
import com.cisco.cpaas.whatsapp.WhatsAppClient;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Response to the {@link WhatsAppClient#sendMessage(WhatsAppMsg)}. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class WhatsAppSendMsgResponse extends WebexResponse {

  private final String messageId;
  private final String correlationId;
  private final Instant acceptedTime;
}
