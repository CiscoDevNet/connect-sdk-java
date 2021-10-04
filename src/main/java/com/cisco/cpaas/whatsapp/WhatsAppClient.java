package com.cisco.cpaas.whatsapp;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.ClientConfigurer;
import com.cisco.cpaas.core.client.WebexClient;
import com.cisco.cpaas.core.client.WebexResponseException;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsg;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.cisco.cpaas.whatsapp.type.WhatsAppSendMsgResponse;

import java.util.Optional;

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
   * @return An optional containing the metadata describing the send message attempt. An empty
   *     optional will be returned in cases where the requested ID was not found.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  Optional<WhatsAppMsgStatus> getStatus(String messageId);
  //TODO: Instead of an optional,  we should always return a response object so the end user has the request id.

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
