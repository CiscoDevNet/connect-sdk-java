package com.cisco.cpaas.whatsapp.type;

import lombok.Value;
import lombok.With;

import java.time.Instant;

/** Response to the {@link com.cisco.cpaas.whatsapp.WhatsAppClient#sendMessage(WhatsAppMsg)}. */
@Value
public class WhatsAppSendMsgResponse {

  @With private final String requestId;
  private final String messageId;
  private final String correlationId;
  private final Instant acceptedTime;
}
