package com.cisco.cpaas.whatsapp;

import com.cisco.cpaas.TestUtils;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.core.parser.JacksonParser;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.whatsapp.type.Audio;
import com.cisco.cpaas.whatsapp.type.Text;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsg;
import com.cisco.cpaas.whatsapp.type.WhatsAppMsgStatus;
import com.cisco.cpaas.whatsapp.type.WhatsAppSendMsgResponse;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import org.apache.hc.core5.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;

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
 * Tests the full integration of the {@link DefaultWhatsAppClient} within the bounds of the SDK.
 * This is done by starting up a wiremock server to mock the Webex REST API and matching predefined
 * requests and responses found in the test resources folder.
 */
@WireMockTest
public class DefaultWhatsAppClientFunctionalTest {

  private static final String REQUESTS_PATH = "/wiremock/__files/whatsapp/requests/";
  private static final String RESPONSES_PATH = "/wiremock/__files/whatsapp/responses/";

  private static final String MESSAGES_PATH = "/v1/whatsapp/messages";

  private static final String FROM = "a_123456789";
  private static final String TO = "+15559994321";
  private static final String AUTH_TOKEN = "authToken";
  private static final String REQUEST_ID = "requestId";

  private DefaultWhatsAppClient client;

  @BeforeEach
  public void init(WireMockRuntimeInfo wireMockRuntimeInfo) {
    String baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    InternalClient internalClient =
        new ApacheSyncInternalClient(baseUrl, AUTH_TOKEN, new JacksonParser());
    client = new DefaultWhatsAppClient(internalClient);
  }

  @Test
  public void shouldSendWhatsAppMessage() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "post_response.json");
    stubWithCommonHeaders(post(MESSAGES_PATH), aResponse().withStatus(202).withBody(jsonResponse));

    Text text =
        Text.builder()
            .content("Hello World!")
            .substitution("firstName", "John")
            .substitution("lastName", "Doe")
            .build();
    WhatsAppMsg request =
        WhatsAppMsg.of(text)
            .from(FROM)
            .to(TO)
            .callbackUrl(URI.create("https://webhook.example.com"))
            .callbackData("customerID123|1234|new_sale")
            .correlationId("correlationId")
            .build();

    WhatsAppSendMsgResponse actual = client.sendMessage(request);
    WhatsAppSendMsgResponse expected =
        new WhatsAppSendMsgResponse(
            "requestId", "messageId", "correlationId", Instant.parse("2021-07-29T13:45:33.404Z"));

    assertThat(actual, Matchers.equalTo(expected));

    String expectedRequest = TestUtils.getFile(REQUESTS_PATH + "whatsapp_send_msg.json");
    verify(
        withCommonPostHeaders(
            postRequestedFor(urlEqualTo(MESSAGES_PATH))
                .withRequestBody(equalToJson(expectedRequest))));
  }

  @Test
  public void shouldGetWhatsAppStatus() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "get_message_status.json");
    stubWithCommonHeaders(get(MESSAGES_PATH + "/messageId"), ok().withBody(jsonResponse));

    WhatsAppMsgStatus expected =
        WhatsAppMsgStatus.builder()
            .messageId("messageId")
            .acceptedTime(Instant.parse("2021-07-29T13:45:33.404Z"))
            .from("a_123456789")
            .to("+19545551212")
            .correlationId("correlationId")
            .status(WhatsAppMsgStatus.Status.QUEUED)
            .statusTime(Instant.parse("2021-07-29T13:45:33.404Z"))
            .content(Audio.of("http://example.com/path/to/audio.m4a"))
            .requestId("requestId")
            .error(new ErrorResponse("7777", "Error"))
            .build();

    WhatsAppMsgStatus actual = client.getStatus("messageId").get();
    assertThat(actual, Matchers.equalTo(expected));
    verify(withCommonHeaders(getRequestedFor(urlEqualTo(MESSAGES_PATH + "/messageId"))));
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
