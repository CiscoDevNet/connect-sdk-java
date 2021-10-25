package com.imiconnect.connect.sms.callback;

import com.imiconnect.connect.core.type.ErrorResponse;
import com.imiconnect.connect.sms.type.SmsMessage;
import com.imiconnect.connect.sms.type.SmsStatus;
import lombok.Value;

import java.time.Instant;

/**
 * Represents the callback request body that will be sent to the callback if supplied with the send
 * message requests.
 *
 * @see com.imiconnect.connect.sms.SmsClient#sendMessage(SmsMessage)
 */
@Value
public class SmsCallback {

  /** Message's unique ID. */
  private final String messageId;

  /** Timestamp reflecting when the message status was updated. */
  private final Instant statusTime;

  /** The message status. */
  private final SmsStatus status;

  /** Additional data that was added to the original send message request. */
  private final String callbackData;

  /** User defined ID that is assigned to an individual message. */
  private final String correlationId;

  /** Error information that will only be populated if the status is FAILED. */
  private final ErrorResponse error;
}
