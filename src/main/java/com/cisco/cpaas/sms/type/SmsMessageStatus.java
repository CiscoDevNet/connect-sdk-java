package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.client.WebexResponse;
import com.cisco.cpaas.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The status metadata of a send SMS message attempt. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SmsMessageStatus extends WebexResponse {

  private final String messageId;
  private final Instant acceptedTime;
  private final String from;
  private final String to;
  private final String correlationId;
  private final String content;
  private final SmsContentType contentType;
  private final String dltTemplateId;
  private final SendStatus status;
  private final Instant statusTime;
  private final ErrorResponse error;

}
