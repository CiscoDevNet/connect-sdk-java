package com.cisco.cpaas.sms;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.ClientConfigurer;
import com.cisco.cpaas.core.client.WebexClient;
import com.cisco.cpaas.core.client.HttpResponseException;
import com.cisco.cpaas.sms.type.SendSmsResponse;
import com.cisco.cpaas.sms.type.SmsMessage;
import com.cisco.cpaas.sms.type.SmsMessageStatus;

/** Interface defining the methods to interact with the Webex SMS Messaging API. */
public interface SmsClient extends WebexClient {

  /**
   * Sends an SMS message.
   *
   * <p>The SMS message includes an idempotency key that will prevent the same message from being
   * processed multiple times on the server. If sending the same message more than once is
   * intentional, a copy should be made.
   *
   * @return The messageId representing the message.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  SendSmsResponse sendMessage(SmsMessage request);

  /**
   * Retrieves the status of an SMS send message attempt.
   *
   * @param messageId The messageId returned from the {{@link #sendMessage(SmsMessage)}} method.
   * @return The metadata describing the send message attempt.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  SmsMessageStatus getStatus(String messageId);

  /**
   * Entry point for creating a new instance of the SmsClient using the available configurers.
   *
   * @return The entry point configurer.
   */
  static ClientConfigurer.UrlStep<SmsClient> create() {
    return new Configurer();
  }

  /** SMS version of the client configurer. */
  class Configurer extends ClientConfigurer.Steps<SmsClient> {

    @Override
    public SmsClient build() {
      return new DefaultSmsClient(new ApacheSyncInternalClient(baseUrl, apiToken, parser));
    }
  }
}
