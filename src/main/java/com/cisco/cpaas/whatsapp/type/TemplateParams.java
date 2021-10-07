package com.cisco.cpaas.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

import java.util.Map;

/**
 * Parameters associated with a WhatsApp {@link TemplateContent} message. TODO: Waiting on API
 * contract to be finished.
 */
@Value
public class TemplateParams {

  @JsonValue private final Map<String, Content> params;
}
