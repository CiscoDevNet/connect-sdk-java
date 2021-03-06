package com.imiconnect.connect.sms.type;

import com.imiconnect.connect.core.client.ConnectResponse;
import com.imiconnect.connect.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The status metadata of a send SMS message attempt. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SmsMessageStatus extends ConnectResponse {

  private final String messageId;
  private final Instant acceptedTime;
  private final String from;
  private final String to;
  private final String correlationId;
  private final String content;
  private final SmsContentType contentType;
  private final String dltTemplateId;
  private final SmsStatus status;
  private final Instant statusTime;
  private final ErrorResponse error;

  @lombok.Builder(builderClassName = "Builder")
  public SmsMessageStatus(
      String requestId,
      String messageId,
      Instant acceptedTime,
      String from,
      String to,
      String correlationId,
      String content,
      SmsContentType contentType,
      String dltTemplateId,
      SmsStatus status,
      Instant statusTime,
      ErrorResponse error) {
    super(requestId);
    this.messageId = messageId;
    this.acceptedTime = acceptedTime;
    this.from = from;
    this.to = to;
    this.correlationId = correlationId;
    this.content = content;
    this.contentType = contentType;
    this.dltTemplateId = dltTemplateId;
    this.status = status;
    this.statusTime = statusTime;
    this.error = error;
  }
}
