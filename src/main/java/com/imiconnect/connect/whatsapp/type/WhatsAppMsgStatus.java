package com.imiconnect.connect.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imiconnect.connect.core.client.ConnectResponse;
import com.imiconnect.connect.core.type.ErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The status of a WhatsApp message. */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true) // TODO: can we remove this now with custom deserializer
public final class WhatsAppMsgStatus extends ConnectResponse {

  public enum Status {
    QUEUED,
    SENT,
    DELIVERED,
    READ,
    FAILED
  }

  private final String messageId;
  private final Instant acceptedTime;
  private final String from;
  private final String to;
  private final String correlationId;
  private final Status status;
  private final Instant statusTime;
  private final Content content;
  private final ErrorResponse error;

  @lombok.Builder(builderClassName = "Builder")
  public WhatsAppMsgStatus(
      String requestId,
      String messageId,
      Instant acceptedTime,
      String from,
      String to,
      String correlationId,
      Status status,
      Instant statusTime,
      Content content,
      ErrorResponse error) {
    super(requestId);
    this.messageId = messageId;
    this.acceptedTime = acceptedTime;
    this.from = from;
    this.to = to;
    this.correlationId = correlationId;
    this.status = status;
    this.statusTime = statusTime;
    this.content = content;
    this.error = error;
  }
}
