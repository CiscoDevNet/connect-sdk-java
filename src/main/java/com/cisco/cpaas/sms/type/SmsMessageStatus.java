package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.type.ErrorResponse;
import lombok.Value;
import lombok.With;

import java.time.Instant;

/** The status metadata of a send SMS message attempt. */
@Value
public final class SmsMessageStatus {

  @With private final String requestId;
  private final String messageId;
  private final Instant acceptedTime;
  private final String from;
  private final String to;
  private final String correlationId;
  private final String content;
  private final SmsContentType contentType;
  private final String dltTemplateId;
  private final String status;
  private final Instant statusTime;
  private final ErrorResponse error;

}
