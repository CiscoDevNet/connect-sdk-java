package com.imiconnect.connect.sms.type;

import com.imiconnect.connect.core.client.ConnectResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Response object for {@link com.imiconnect.connect.sms.SmsClient#sendMessage(SmsMessage)}. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SendSmsResponse extends ConnectResponse {

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
