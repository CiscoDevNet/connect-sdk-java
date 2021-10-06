package com.cisco.cpaas.voice;

import com.cisco.cpaas.TestUtils;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.core.parser.JacksonParser;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.core.type.PhoneNumber;
import com.cisco.cpaas.voice.engine.Style;
import com.cisco.cpaas.voice.engine.Voice;
import com.cisco.cpaas.voice.engine.azure.Gender;
import com.cisco.cpaas.voice.type.Audio;
import com.cisco.cpaas.voice.type.Call;
import com.cisco.cpaas.voice.type.CallRecordings;
import com.cisco.cpaas.voice.type.CallState;
import com.cisco.cpaas.voice.type.CallStatus;
import com.cisco.cpaas.voice.type.PlayAndDrop;
import com.cisco.cpaas.voice.type.PlayAndDropResponse;
import com.cisco.cpaas.voice.type.Recording;
import com.cisco.cpaas.voice.type.StartCallResponse;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.sun.tools.javac.util.List;
import org.apache.hc.core5.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the full integration of the {@link DefaultVoiceClient} within the bounds of the SDK. This
 * is done by starting up a wiremock server to mock the Webex REST API and matching predefined
 * requests and responses found in the test resources folder.
 */
@WireMockTest
public class DefaultVoiceClientFunctionalTestIT {

  private static final String REQUESTS_PATH = "/wiremock/__files/voice/requests/";
  private static final String RESPONSES_PATH = "/wiremock/__files/voice/responses/";

  private static final String CALLS_PATH = "/v1/voice/calls";
  private static final String MESSAGES_PATH = "/v1/voice/messages";

  private static final String CALLER_ID = "+15550001234";
  private static final String DIAL_TO = "+15559994321";
  private static final String AUTH_TOKEN = "authToken";
  private static final String REQUEST_ID = "requestId";

  private DefaultVoiceClient client;

  @BeforeEach
  public void init(WireMockRuntimeInfo wireMockRuntimeInfo) {
    String baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    InternalClient internalClient =
        new ApacheSyncInternalClient(baseUrl, AUTH_TOKEN, new JacksonParser());
    client = new DefaultVoiceClient(internalClient);
  }

  @ParameterizedTest
  @MethodSource("audioTypes")
  public void shouldSendPlayAndDropMessage(String requestFilename, Audio audio) {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "post_response.json");
    stubWithCommonHeaders(post(MESSAGES_PATH), aResponse().withStatus(202).withBody(jsonResponse));

    PlayAndDropResponse response =
        client.playAndDrop(
            PlayAndDrop.from(CALLER_ID)
                .to(DIAL_TO)
                .callbackUrl(URI.create("https://webhook.example.com"))
                .correlationId("correlationId")
                .audio(audio)
                .build());

    assertThat(response.getSessionId(), Matchers.equalTo("sessionId"));
    assertThat(response.getStatus(), Matchers.equalTo(CallState.QUEUED));
    assertThat(response.getRequestId(), Matchers.equalTo(REQUEST_ID));

    String expectedRequest = TestUtils.getFile(REQUESTS_PATH + requestFilename);

    verify(
        withCommonPostHeaders(
            postRequestedFor(urlEqualTo(MESSAGES_PATH))
                .withRequestBody(equalToJson(expectedRequest))));
  }

  private static Stream<Arguments> audioTypes() {
    return Stream.of(
        Arguments.of(
            "play_and_drop_url.json",
            Audio.ofUrl(URI.create("https://bucket.example.com/audio.mp3"))),
        Arguments.of(
            "play_and_drop_tts.json",
            Audio.ofTtsText(
                "Hello World!",
                Voice.azure()
                    .voice("Aria")
                    .gender(Gender.FEMALE)
                    .style(Style.STANDARD)
                    .language("en_US")
                    .build())),
        Arguments.of("play_and_drop_media.json", Audio.ofMediaId("mediaId")));
  }

  @Test
  public void shouldStartCallSession() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "post_response.json");
    stubWithCommonHeaders(post(CALLS_PATH), aResponse().withStatus(202).withBody(jsonResponse));

    StartCallResponse response =
        client.startCall(
            Call.from(CALLER_ID)
                .to(DIAL_TO)
                .callbackUrl(URI.create("https://webhook.example.com"))
                .correlationId("correlationId")
                .detectVoiceMail(false)
                .recordCallSeconds(10)
                .build());

    assertThat(response.getSessionId(), Matchers.equalTo("sessionId"));
    assertThat(response.getStatus(), Matchers.equalTo(CallState.QUEUED));
    assertThat(response.getRequestId(), Matchers.equalTo(REQUEST_ID));

    String expectedRequest = TestUtils.getFile(REQUESTS_PATH + "start_call.json");

    verify(
        withCommonPostHeaders(
            postRequestedFor(urlEqualTo(CALLS_PATH))
                .withRequestBody(equalToJson(expectedRequest))));
  }

  @Test
  public void shouldGetCallStatus() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "get_status_response.json");
    stubWithCommonHeaders(get(CALLS_PATH + "/sessionId"), ok().withBody(jsonResponse));
    CallStatus actual = client.getCallStatus("sessionId");

    CallStatus expected =
        CallStatus.builder()
            .callerId(PhoneNumber.of(CALLER_ID))
            .dialedNumber(PhoneNumber.of(DIAL_TO))
            .status(CallState.COMPLETED)
            .correlationId("correlationId")
            .durationSeconds(305)
            .requestId(REQUEST_ID)
            .offeredTime(Instant.parse("2021-06-01T12:30:13.495Z"))
            .answeredTime(Instant.parse("2021-06-01T12:30:16.950Z"))
            .error(new ErrorResponse("7000", "error"))
            .sessionId("sessionId")
            .build();

    assertThat(actual, Matchers.equalTo(expected));
    verify(withCommonHeaders(getRequestedFor(urlEqualTo(CALLS_PATH + "/sessionId"))));
  }

  @Test
  public void shouldGetCallRecordings() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "get_recordings_response.json");
    stubWithCommonHeaders(get(CALLS_PATH + "/sessionId/recordings"), ok().withBody(jsonResponse));
    CallRecordings actual = client.getCallRecordings("sessionId");

    CallRecordings expected =
        new CallRecordings(
            REQUEST_ID,
            "sessionId",
            List.of(
                new Recording(
                    609,
                    URI.create(
                        "https://webex.com/v1/voice/calls/sessionId/recordings/file_123.wav"))));

    assertThat(actual, Matchers.equalTo(expected));
    verify(withCommonHeaders(getRequestedFor(urlEqualTo(CALLS_PATH + "/sessionId/recordings"))));
  }

  // Verify the idempotency key is set on POST
  private RequestPatternBuilder withCommonPostHeaders(RequestPatternBuilder requestMatcher) {
    requestMatcher.withHeader("Idempotency-Key", WireMock.matching("^[\\w=\\-]+$"));
    return withCommonHeaders(requestMatcher);
  }

  // Verify the authorization header is sent.
  private RequestPatternBuilder withCommonHeaders(RequestPatternBuilder requestMatcher) {
    requestMatcher.withHeader("Authorization", WireMock.equalTo("Bearer " + AUTH_TOKEN));
    return requestMatcher;
  }

  // Attach the request response header and content type.
  private void stubWithCommonHeaders(
    MappingBuilder requestBuilder, ResponseDefinitionBuilder responseBuilder) {
    stubFor(
        requestBuilder.willReturn(
            responseBuilder
                .withHeader("Request-Id", REQUEST_ID)
                .withHeader("Content-Type", ContentType.APPLICATION_JSON.toString())));
  }

}
