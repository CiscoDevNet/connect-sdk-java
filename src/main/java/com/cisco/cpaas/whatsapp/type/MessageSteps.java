package com.cisco.cpaas.whatsapp.type;

import java.net.URI;

/**
 * Collection of interfaces that are used to define the sequence of steps for creating a new {@link
 * WhatsAppMsg}.
 */
public interface MessageSteps {

  /** Step 1, add content. */
  interface ContentCreator {
    /**
     * Add one of the types of {@link Content} to the WhatsApp message.
     *
     * @param content The content to add.
     * @return Step2.
     */
    From content(Content content);
  }

  /** Step 2 add a from endpoint. */
  interface From {
    /**
     * Add a from endpoint.
     *
     * @param from The endpoint that the message will be sent from.
     * @return Step 3
     */
    To from(String from);
  }

  /** Step 3 add a send-to phone number. */
  interface To {
    /**
     * Add a send-to phone number E.164 format required.
     *
     * @param to the send-to endpoint.
     * @return Options
     */
    Options to(String to);
  }

  /** Step 4, add any optional data. */
  interface Options {

    Options callbackUrl(String callbackUrl);

    /**
     * Optional URL where a notification callback can be received.
     *
     * @param callbackUrl The URL specifying where the callback webhook can be accessed.
     * @return Options
     */
    Options callbackUrl(URI callbackUrl);

    /**
     * Optional data that can be included in the callback request. This field is ignored if no
     * {@link #callbackUrl(URI)} is specified.
     *
     * @param callbackData The data to include in the callback.
     * @return Options
     */
    Options callbackData(String callbackData);

    /**
     * An optional, arbitrary identifier to track the message.
     *
     * @param correlationId The identifier.
     * @return Options
     */
    Options correlationId(String correlationId);

    // TODO: Substitutions are still being defined.
    Options substitutions(Substitutions substitutions);

    WhatsAppMsg build();
  }

}
