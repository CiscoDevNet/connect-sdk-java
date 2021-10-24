package com.imiconnect.connect.voice;

import com.imiconnect.connect.core.ConnectException;
import com.imiconnect.connect.core.client.ApacheSyncInternalClient;
import com.imiconnect.connect.core.client.ClientConfigurer;
import com.imiconnect.connect.core.client.ConnectClient;
import com.imiconnect.connect.core.client.HttpResponseException;
import com.imiconnect.connect.voice.type.Call;
import com.imiconnect.connect.voice.type.CallRecordings;
import com.imiconnect.connect.voice.type.CallStatus;
import com.imiconnect.connect.voice.type.PlayAndDrop;
import com.imiconnect.connect.voice.type.PlayAndDropResponse;
import com.imiconnect.connect.voice.type.StartCallResponse;

/** Interface defining the methods to interact with the Connect platform Voice API. */
// TODO: Expand on all javadocs...
public interface VoiceClient extends ConnectClient {

  /**
   * Places a new voice call to telephone number, plays a voice message and then disconnects the
   * call.
   *
   * @param request The request defining the parameters of the call and message to be played.
   * @return The response object containing the session ID referencing this call.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
   */
  PlayAndDropResponse playAndDrop(PlayAndDrop request);

  /**
   * Places a new call to a telephone number.
   *
   * @param request The request defining the parameters of the call and message to be played.
   * @return The response object containing the session ID referencing this call.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
   */
  // TODO: Make this take a request since Call can be of the wrong type.
  StartCallResponse startCall(Call request);

  /**
   * Retrieves the status of a previously placed call.
   *
   * @param sessionId The session ID retrieved from a previously placed call.
   * @return The status of the call.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
   */
  CallStatus getCallStatus(String sessionId);

  /**
   * Retrieves any call recording that resulted from a previously placed call.
   *
   * @param sessionId The session ID retrieved from a previously placed call.
   * @return A list of recordings.
   * @throws HttpResponseException when the service returns a non-successful response.
   * @throws ConnectException when any error occurs that is not related to the http response status.
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
