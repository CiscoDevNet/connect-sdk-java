package com.imiconnect.cpaas.sms;

import com.imiconnect.cpaas.core.client.InternalClient;
import com.imiconnect.cpaas.sms.type.SendSmsResponse;
import com.imiconnect.cpaas.sms.type.SmsMessage;
import com.imiconnect.cpaas.sms.type.SmsMessageStatus;

import static com.imiconnect.cpaas.core.util.Preconditions.notNullOrBlank;
import static java.util.Objects.requireNonNull;

/**
 * Adapts the {@link SmsClient} interface to work with the common {@link InternalClient}
 * implementation.
 */
final class DefaultSmsClient implements SmsClient {

  private static final String RESOURCE_PATH = "/v1/sms/messages";

  private final InternalClient client;

  public DefaultSmsClient(InternalClient client) {
    this.client = requireNonNull(client, "InternalClient can not be null.");
  }

  @Override
  public synchronized void refreshToken(String apiToken) {
    notNullOrBlank(apiToken, "api token");
    client.refreshToken(apiToken);
  }

  @Override
  public SendSmsResponse sendMessage(SmsMessage request) {
    requireNonNull(request, "send sms request can not be null.");
    return client.post(RESOURCE_PATH, request, SendSmsResponse.class);
  }

  @Override
  public SmsMessageStatus getStatus(String messageId) {
    notNullOrBlank(messageId, "messageId");
    String path = RESOURCE_PATH + "/" + messageId;
    return client.get(path, SmsMessageStatus.class);
  }
}
