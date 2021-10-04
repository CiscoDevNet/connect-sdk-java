package com.cisco.cpaas.whatsapp;

import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsg;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.cisco.cpaas.whatsapp.type.WhatsAppSendMsgResponse;

import java.util.Optional;

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
    return httpClient.post(RESOURCE_PATH, message, WhatsAppSendMsgResponse.class);
  }

  @Override
  public Optional<WhatsAppMsgStatus> getStatus(String messageId) {
    String path = RESOURCE_PATH + "/" + messageId;
    return Optional.ofNullable(httpClient.get(path, WhatsAppMsgStatus.class));
  }

  @Override
  public void refreshToken(String apiToken) {
    httpClient.refreshToken(apiToken);
  }
}
