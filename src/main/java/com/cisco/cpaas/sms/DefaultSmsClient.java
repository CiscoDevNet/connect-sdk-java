package com.cisco.cpaas.sms;

import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.sms.type.SendSmsResponse;
import com.cisco.cpaas.sms.type.SmsMessage;
import com.cisco.cpaas.sms.type.SmsMessageStatus;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Adapts the {@link SmsClient} interface to work with the common {@link InternalClient}
 * implementation.
 */
final class DefaultSmsClient implements SmsClient {

  private final InternalClient client;

  public DefaultSmsClient(InternalClient client) {
    this.client = requireNonNull(client, "InternalClient can not be null.");
  }

  @Override
  public void refreshToken(String apiToken) {
    client.refreshToken(apiToken);
  }

  @Override
  public SendSmsResponse sendMessage(SmsMessage request) {
    return client.post(request, SendSmsResponse.class);
  }

  @Override
  public Optional<SmsMessageStatus> getStatus(String messageId) {
    return Optional.ofNullable(client.get(messageId, SmsMessageStatus.class));
  }
}
