package com.cisco.cpaas.whatsapp.type;

import com.cisco.cpaas.core.client.WebexResponse;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

/** The status of a WhatsApp message. */
@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WhatsAppMsgStatus extends WebexResponse {

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
}
