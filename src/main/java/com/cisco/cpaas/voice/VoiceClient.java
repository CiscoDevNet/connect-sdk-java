package com.cisco.cpaas.voice;

import com.cisco.cpaas.core.WebexException;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.ClientConfigurer;
import com.cisco.cpaas.core.client.WebexClient;
import com.cisco.cpaas.core.client.WebexResponseException;
import com.cisco.cpaas.voice.type.Call;
import com.cisco.cpaas.voice.type.CallRecordings;
import com.cisco.cpaas.voice.type.CallStatus;
import com.cisco.cpaas.voice.type.PlayAndDrop;
import com.cisco.cpaas.voice.type.PlayAndDropResponse;
import com.cisco.cpaas.voice.type.StartCallResponse;

/** Interface defining the methods to interact with the Webex Voice API. */
// TODO: Expand on all javadocs...
public interface VoiceClient extends WebexClient {

  /**
   * Places a new voice call to telephone number, plays a voice message and then disconnects the
   * call.
   *
   * @param request The request defining the parameters of the call and message to be played.
   * @return The response object containing the session ID referencing this call.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  PlayAndDropResponse playAndDrop(PlayAndDrop request);

  /**
   * Places a new call to a telephone number.
   *
   * @param request The request defining the parameters of the call and message to be played.
   * @return The response object containing the session ID referencing this call.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  // TODO: Make this take a request since Call can be of the wrong type.
  StartCallResponse startCall(Call request);

  /**
   * Retrieves the status of a previously placed call.
   *
   * @param sessionId The session ID retrieved from a previously placed call.
   * @return The status of the call.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  CallStatus getCallStatus(String sessionId);

  /**
   * Retrieves any call recording that resulted from a previously placed call.
   *
   * @param sessionId The session ID retrieved from a previously placed call.
   * @return A list of recordings.
   * @throws WebexResponseException when the service returns a non-successful response.
   * @throws WebexException when any error occurs that is not related to the http response status.
   */
  CallRecordings getCallRecordings(String sessionId);

  /**
   * Entry point for creating a new instance of the WhatsAppClient using the available configurers.
   *
   * @return The entry point configurer.
   */
  static ClientConfigurer.UrlStep<VoiceClient> create() {
    return new VoiceClient.Configurer();
  }

  /** WhatsApp version of the client configurer. */
  class Configurer extends ClientConfigurer.Steps<VoiceClient> {

    @Override
    public VoiceClient build() {
      return new DefaultVoiceClient(new ApacheSyncInternalClient(baseUrl, apiToken, parser));
    }
  }
}
