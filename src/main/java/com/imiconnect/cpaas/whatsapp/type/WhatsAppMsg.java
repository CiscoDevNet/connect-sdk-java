package com.imiconnect.cpaas.whatsapp.type;

import com.imiconnect.cpaas.core.annotation.Nullable;
import com.imiconnect.cpaas.core.type.Idempotent;
import com.imiconnect.cpaas.core.type.PhoneNumber;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/** API to interact with a WhatsApp message request. */
public interface WhatsAppMsg extends Idempotent {

  public String getFrom();

  public PhoneNumber getTo();

  public @Nullable URI getCallbackUrl();

  public @Nullable String getCallbackData();

  public @Nullable String getCorrelationId();

  public Content getContent();

  /**
   * Returns a specific implementation type of to associated content. This is done with a simple
   * cast, saving the extra step for the user.
   *
   * @param <T> The specific type of content.
   * @return The specific implementation of the content.
   * @throws ClassCastException if the desired type does not match the content.
   */
  <T extends Content> T getCastContent();

  /**
   * Creates a new WhatsApp message containing {@link Text} content.
   *
   * @param textMessage the text message to send.
   * @return A builder to generate the rest of the message parameters.
   */
  public static MessageSteps.From ofText(String textMessage) {
    return new WhatsAppSendMsgRequest.Builder().content(Text.of(textMessage));
  }

  /**
   * Creates a new WhatsApp message containing any type of {@link Content}.
   *
   * @param content The content to add to the message.
   * @return
   */
  public static MessageSteps.From of(Content content) {
    requireNonNull(content, "content can not be null");
    return new WhatsAppSendMsgRequest.Builder().content(content);
  }
}
