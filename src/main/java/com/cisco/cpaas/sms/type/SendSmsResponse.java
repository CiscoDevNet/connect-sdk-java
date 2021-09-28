package com.cisco.cpaas.sms.type;

import lombok.Value;
import lombok.With;

import java.time.Instant;

/** Response object for {@link com.cisco.cpaas.sms.SmsClient#sendMessage(SmsMessage)}. */
@Value
public class SendSmsResponse {

  @With private final String requestId;
  private final Instant acceptedTime;
  private final String messageId;
  private final String correlationId;

  public SendSmsResponse(
      String requestId, Instant acceptedTime, String messageId, String correlationId) {
    this.requestId = requestId;
    this.acceptedTime = acceptedTime;
    this.messageId = messageId;
    this.correlationId = correlationId;
  }
}
