package com.imiconnect.connect.whatsapp.callback;

import com.imiconnect.connect.core.type.ErrorResponse;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsgStatus;
import lombok.Value;

import java.time.Instant;

@Value
public final class WhatsAppCallback {

  /** Message's unique ID. */
  private final String messageId;

  /** Timestamp reflecting when the message status was updated. */
  private final Instant statusTime;

  /** The message status. */
  private final WhatsAppMsgStatus.Status status;

  /** Additional data that was added to the original send message request. */
  private final String callbackData;

  /** User defined ID that is assigned to an individual message. */
  private final String correlationId;

  /** Error information that will only be populated if the status is FAILED. */
  private final ErrorResponse error;
}
