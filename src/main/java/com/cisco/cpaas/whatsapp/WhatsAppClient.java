package com.cisco.cpaas.whatsapp;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.ClientConfigurer;
import com.cisco.cpaas.core.client.WebexClient;
import com.cisco.cpaas.core.client.WebexResponseException;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsg;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.cisco.cpaas.whatsapp.type.WhatsAppSendMsgResponse;

/** Interface defining the methods to interact with the Webex Whatsapp API. */
public interface WhatsAppClient extends WebexClient {

  /**
   * Sends a whatsapp message.
   *
   * @return The messageId representing the message.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  WhatsAppSendMsgResponse sendMessage(WhatsAppMsg message);

  /**
   * Retrieves the metadata of a whatsapp message send attempt.
   *
   * @param messageId The id returned from the {{@link #sendMessage(WhatsAppMsg)}} method.
   * @return The metadata describing the send message attempt. An exception will be thrown if the ID
   *     is not found.
   * @throws WebexResponseException or its subclasses when the service returns a non-successful
   *     response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  WhatsAppMsgStatus getStatus(String messageId);

  /**
   * Entry point for creating a new instance of the WhatsAppClient using the available configurers.
   *
   * @return The entry point configurer.
   */
  static ClientConfigurer.UrlStep<WhatsAppClient> create() {
    return new Configurer();
  }

  /** WhatsApp version of the client configurer. */
  class Configurer extends ClientConfigurer.Steps<WhatsAppClient> {

    @Override
    public WhatsAppClient build() {
      return new DefaultWhatsAppClient(new ApacheSyncInternalClient(baseUrl, apiToken, parser));
    }
  }
}
