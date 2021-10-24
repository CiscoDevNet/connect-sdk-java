package com.imiconnect.connect.whatsapp;

import com.imiconnect.connect.core.ConnectException;
import com.imiconnect.connect.core.client.ApacheSyncInternalClient;
import com.imiconnect.connect.core.client.ClientConfigurer;
import com.imiconnect.connect.core.client.ConnectClient;
import com.imiconnect.connect.core.client.HttpResponseException;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsg;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsgStatus;
import com.imiconnect.connect.whatsapp.type.WhatsAppSendMsgResponse;

/** Interface defining the methods to interact with the Connect platform Whatsapp API. */
public interface WhatsAppClient extends ConnectClient {

  /**
   * Sends a whatsapp message.
   *
   * @return The messageId representing the message.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
   */
  WhatsAppSendMsgResponse sendMessage(WhatsAppMsg message);

  /**
   * Retrieves the metadata of a whatsapp message send attempt.
   *
   * @param messageId The id returned from the {{@link #sendMessage(WhatsAppMsg)}} method.
   * @return The metadata describing the send message attempt. An exception will be thrown if the ID
   *     is not found.
   * @throws HttpResponseException or its subclasses when the service returns a non-successful
   *     response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
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
