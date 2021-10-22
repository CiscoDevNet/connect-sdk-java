package com.imiconnect.cpaas.sms.type;

import com.imiconnect.cpaas.core.client.WebexResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Response object for {@link com.imiconnect.cpaas.sms.SmsClient#sendMessage(SmsMessage)}. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SendSmsResponse extends WebexResponse {

  private final Instant acceptedTime;
  private final String messageId;
  private final String correlationId;

  public SendSmsResponse(
      String requestId, Instant acceptedTime, String messageId, String correlationId) {
    super(requestId);
    this.acceptedTime = acceptedTime;
    this.messageId = messageId;
    this.correlationId = correlationId;
  }
}
