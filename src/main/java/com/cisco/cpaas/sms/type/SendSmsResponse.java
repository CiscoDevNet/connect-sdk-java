package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.client.WebexResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** Response object for {@link com.cisco.cpaas.sms.SmsClient#sendMessage(SmsMessage)}. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SendSmsResponse extends WebexResponse {

  private final Instant acceptedTime;
  private final String messageId;
  private final String correlationId;

}
