package com.cisco.cpaas.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Defines the objects that can be used as content in a {@link SendMessageRequest}.
 *
 * <p>Unknown properties are ignored during deserialization to account for the flattened response
 * object received from the webex service.
 *
 * @see com.cisco.cpaas.whatsapp.parser.WhatsAppMsgStatusDeserializer for more info.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "contentType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Text.class, name = "TEXT"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Audio.class, name = "AUDIO"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Contacts.class, name = "CONTACTS"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Document.class, name = "DOCUMENT"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Image.class, name = "IMAGE"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Video.class, name = "VIDEO"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Location.class, name = "LOCATION"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.Sticker.class, name = "STICKER"),
  @JsonSubTypes.Type(value = com.cisco.cpaas.whatsapp.type.TemplateContent.class, name = "TEMPLATE")
})
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"contentType"})
public interface Content {

  /** Gets the type of content. */
  public WhatsAppContentType getContentType();
}
