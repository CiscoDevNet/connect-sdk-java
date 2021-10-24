package com.imiconnect.connect.whatsapp.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Defines the objects that can be used as content in a {@link WhatsAppSendMsgRequest}.
 *
 * <p>Unknown properties are ignored during deserialization to account for the flattened response
 * object received from the Connect service.
 *
 * @see com.imiconnect.connect.whatsapp.parser.WhatsAppMsgStatusDeserializer for more info.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "contentType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Text.class, name = "TEXT"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Audio.class, name = "AUDIO"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Contacts.class, name = "CONTACTS"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Document.class, name = "DOCUMENT"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Image.class, name = "IMAGE"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Video.class, name = "VIDEO"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Location.class, name = "LOCATION"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Sticker.class, name = "STICKER"),
  @JsonSubTypes.Type(value = com.imiconnect.connect.whatsapp.type.Template.class, name = "TEMPLATE")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Content {

  /** Gets the type of content. */
  public WhatsAppContentType getContentType();
}
