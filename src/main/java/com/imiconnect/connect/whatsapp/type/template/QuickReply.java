package com.imiconnect.connect.whatsapp.type.template;

import com.imiconnect.connect.whatsapp.type.Template;
import lombok.Value;

import static com.imiconnect.connect.core.util.Preconditions.notNullOrBlank;

/**
 * Represents a "quick reply" button that can be added to a whatsapp {@link Template}. There
 * can only be a maximum of 3 for a single message.
 */
@Value
public final class QuickReply {

  /** The text displayed on the button. Must match the value defined in the connect platform. */
  private final String buttonText;

  /** The data that is sent when a user clicks the button. */
  private final String payload;

  QuickReply(String buttonText, String payload) {
    this.buttonText = notNullOrBlank(buttonText, "buttonText");
    this.payload = notNullOrBlank(payload, "payload");
  }

  public static QuickReply of(String buttonText, String payload) {
    return new QuickReply(buttonText, payload);
  }
}
