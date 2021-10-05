package com.cisco.cpaas.voice;

import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.voice.type.Call;
import com.cisco.cpaas.voice.type.CallRecordings;
import com.cisco.cpaas.voice.type.CallStatus;
import com.cisco.cpaas.voice.type.PlayAndDrop;
import com.cisco.cpaas.voice.type.PlayAndDropResponse;
import com.cisco.cpaas.voice.type.StartCallResponse;

/**
 * The default voice messaging client that adapts the {@link VoiceClient} api to use an
 * implementation of the {@link InternalClient}.
 */
final class DefaultVoiceClient implements VoiceClient {

  private static final String MESSAGES_PATH = "/v1/voice/messages";
  private static final String CALLS_PATH = "/v1/voice/calls";

  private final InternalClient httpClient;

  public DefaultVoiceClient(InternalClient internalClient) {
    this.httpClient = internalClient;
  }

  @Override
  public void refreshToken(String apiToken) {
    httpClient.refreshToken(apiToken);
  }

  @Override
  public PlayAndDropResponse playAndDrop(PlayAndDrop request) {
    return httpClient.post(MESSAGES_PATH, request, PlayAndDropResponse.class);
  }

  @Override
  public StartCallResponse startCall(Call request) {
    return httpClient.post(CALLS_PATH, request, StartCallResponse.class);
  }

  @Override
  public CallStatus getCallStatus(String sessionId) {
    String path = CALLS_PATH + "/" + sessionId;
    return httpClient.get(path, CallStatus.class);
  }

  @Override
  public CallRecordings getCallRecordings(String sessionId) {
    String path = CALLS_PATH + "/" + sessionId + "/recordings";
    return httpClient.get(path, CallRecordings.class);
  }
}
