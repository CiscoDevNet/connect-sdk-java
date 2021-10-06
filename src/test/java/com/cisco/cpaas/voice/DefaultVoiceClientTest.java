package com.cisco.cpaas.voice;

import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.core.type.PhoneNumber;
import com.cisco.cpaas.voice.type.Audio;
import com.cisco.cpaas.voice.type.Call;
import com.cisco.cpaas.voice.type.CallRecordings;
import com.cisco.cpaas.voice.type.CallState;
import com.cisco.cpaas.voice.type.CallStatus;
import com.cisco.cpaas.voice.type.PlayAndDrop;
import com.cisco.cpaas.voice.type.PlayAndDropResponse;
import com.cisco.cpaas.voice.type.Recording;
import com.cisco.cpaas.voice.type.StartCallResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Unit tests for the {@link DefaultVoiceClient}. */
@ExtendWith(MockitoExtension.class)
class DefaultVoiceClientTest {

  @Mock private InternalClient internalClient;

  private DefaultVoiceClient client;

  @BeforeEach
  public void init() {
    client = new DefaultVoiceClient(internalClient);
  }

  @Test
  public void shouldSendPlayAndDropMessage() {
    PlayAndDropResponse expected = new PlayAndDropResponse(null, "sessionId", CallState.QUEUED);
    when(internalClient.post(anyString(), any(), any())).thenReturn(expected);

    PlayAndDrop msg =
        PlayAndDrop
            .from("+15550001234")
            .to("+15559994321")
            .audio(Audio.ofUrl(URI.create("http://bucket.example.com/audio.mp3")))
            .build();
    PlayAndDropResponse actual = client.playAndDrop(msg);

    verify(internalClient).post("/v1/voice/messages", msg, PlayAndDropResponse.class);
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void shouldStartCallSession() {
    StartCallResponse expected = new StartCallResponse(null, "sessionId", CallState.QUEUED);
    when(internalClient.post(anyString(), any(), any())).thenReturn(expected);

    Call msg = Call.from("+15550001234").to("+15559994321").build();
    StartCallResponse actual = client.startCall(msg);

    verify(internalClient).post("/v1/voice/calls", msg, StartCallResponse.class);
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void shouldGetCallStatus() {
    CallStatus expected = CallStatus.builder()
      .callerId(PhoneNumber.of("+15550001234"))
      .dialedNumber(PhoneNumber.of("+15559994321"))
      .status(CallState.QUEUED)
      .correlationId("correlationId")
      .durationSeconds(500)
      .offeredTime(Instant.now())
      .answeredTime(Instant.now())
      .sessionId("sessionId")
      .build();
    when(internalClient.get(anyString(), any())).thenReturn(expected);
    CallStatus actual = client.getCallStatus("sessionId");

    verify(internalClient).get("/v1/voice/calls/sessionId", CallStatus.class);
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void shouldGetCallRecordings() {
    List<Recording> recordings = new ArrayList<>();
    recordings.add(new Recording(345, URI.create("http://audio.location.com/audio.wav")));
    CallRecordings expected = new CallRecordings(null, "sessionId", recordings);

    when(internalClient.get(anyString(), any())).thenReturn(expected);
    CallRecordings actual = client.getCallRecordings("sessionId");

    verify(internalClient).get("/v1/voice/calls/sessionId/recordings", CallRecordings.class);
    assertThat(actual, equalTo(expected));
  }
}
