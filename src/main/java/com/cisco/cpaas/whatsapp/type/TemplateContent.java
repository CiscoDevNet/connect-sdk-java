package com.cisco.cpaas.whatsapp.type;

import lombok.NonNull;
import lombok.Value;

/** Defines whatsapp {@link Content} representing a templated message. */
@Value
@lombok.Builder(builderClassName = "Builder")
public class TemplateContent implements Content {

  private final WhatsAppContentType contentType = WhatsAppContentType.TEMPLATE;
  @NonNull private final String templateId;
  @NonNull private final TemplateParams parameters;

}
