package com.imiconnect.connect.whatsapp;

import com.imiconnect.connect.core.client.InternalClient;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsg;
import com.imiconnect.connect.whatsapp.type.WhatsAppMsgStatus;
import com.imiconnect.connect.whatsapp.type.WhatsAppSendMsgResponse;

import static com.imiconnect.connect.core.util.Preconditions.notNullOrBlank;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of the {@link WhatsAppClient} that adapts the public API to use the internal
 * apache rest client.
 */
final class DefaultWhatsAppClient implements WhatsAppClient {

  private static final String RESOURCE_PATH = "/v1/whatsapp/messages";

  private final InternalClient httpClient;

  public DefaultWhatsAppClient(InternalClient internalClient) {
    this.httpClient = requireNonNull(internalClient, "internal client can not be null.");
  }

  @Override
  public WhatsAppSendMsgResponse sendMessage(WhatsAppMsg message) {
    requireNonNull(message, "WhatsApp send message request can not be null");
    return httpClient.post(RESOURCE_PATH, message, WhatsAppSendMsgResponse.class);
  }

  @Override
  public WhatsAppMsgStatus getStatus(String messageId) {
    notNullOrBlank(messageId, "messageId");
    String path = RESOURCE_PATH + "/" + messageId;
    return httpClient.get(path, WhatsAppMsgStatus.class);
  }

  @Override
  public synchronized void refreshToken(String apiToken) {
    notNullOrBlank(apiToken, "api token");
    httpClient.refreshToken(apiToken);
  }
}
