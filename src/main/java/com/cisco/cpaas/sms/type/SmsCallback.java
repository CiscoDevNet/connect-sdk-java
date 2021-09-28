package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.type.ErrorResponse;
import lombok.Value;

import java.time.Instant;

/**
 * Represents the callback request body that will be sent to the callback if supplied with the send
 * message requests.
 *
 * @see com.cisco.cpaas.sms.SmsClient#sendMessage(SmsMessage)
 */
@Value
public class SmsCallback {

  private final String messageId;
  private final Instant statusTime;
  private final SendStatus status;
  private final String callbackData;
  private final String correlationId;
  private final ErrorResponse error;
}
