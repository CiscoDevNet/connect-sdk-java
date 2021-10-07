package com.cisco.cpaas.sms;

import com.cisco.cpaas.TestUtils;
import com.cisco.cpaas.core.client.ApacheSyncInternalClient;
import com.cisco.cpaas.core.client.InternalClient;
import com.cisco.cpaas.core.parser.JacksonParser;
import com.cisco.cpaas.core.type.ErrorResponse;
import com.cisco.cpaas.sms.type.SendSmsResponse;
import com.cisco.cpaas.sms.type.SendStatus;
import com.cisco.cpaas.sms.type.SmsContentType;
import com.cisco.cpaas.sms.type.SmsMessageRequest;
import com.cisco.cpaas.sms.type.SmsMessageStatus;
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
 * Tests the full integration of the {@link DefaultSmsClient} within the bounds of the SDK. This is
 * done by starting up a wiremock server to mock the Webex REST API and matching predefined requests
 * and responses found in the test resources folder.
 */
@WireMockTest
public class DefaultSmsClientFunctionalTest {

  private static final String REQUESTS_PATH = "/wiremock/__files/sms/requests/";
  private static final String RESPONSES_PATH = "/wiremock/__files/sms/responses/";

  private static final String MESSAGES_PATH = "/v1/sms/messages";

  private static final String FROM = "34343";
  private static final String TO = "+15559994321";
  private static final String AUTH_TOKEN = "authToken";
  private static final String REQUEST_ID = "requestId";

  private DefaultSmsClient client;

  @BeforeEach
  public void init(WireMockRuntimeInfo wireMockRuntimeInfo) {
    String baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    InternalClient internalClient =
        new ApacheSyncInternalClient(baseUrl, AUTH_TOKEN, new JacksonParser());
    client = new DefaultSmsClient(internalClient);
  }

  @Test
  public void shouldSendSmsMessage() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "post_response.json");
    stubWithCommonHeaders(post(MESSAGES_PATH), aResponse().withStatus(202).withBody(jsonResponse));

    SendSmsResponse actual =
        client.sendMessage(
            SmsMessageRequest.of("Hello world!")
                .from(FROM)
                .to(TO)
                .callbackData("customerID123|1234|new_sale")
                .callbackUrl(URI.create("https://webhook.example.com"))
                .dltTemplateId("dltTemplateId")
                .correlationId("correlationId")
                .substitution("key1", "value1")
                .substitution("key2", "value2")
                .build());

    SendSmsResponse expected =
        new SendSmsResponse(
            "requestId", Instant.parse("2021-07-29T13:45:33.404Z"), "messageId", "correlationId");
    assertThat(actual, Matchers.equalTo(expected));

    String jsonRequest = TestUtils.getFile(REQUESTS_PATH + "send_sms_request.json");
    verify(
        withCommonPostHeaders(
            postRequestedFor(urlEqualTo(MESSAGES_PATH)).withRequestBody(equalToJson(jsonRequest))));
  }

  @Test
  public void shouldGetSmsStatus() {
    String jsonResponse = TestUtils.getFile(RESPONSES_PATH + "sms_message_status.json");
    stubWithCommonHeaders(get(MESSAGES_PATH + "/messageId"), ok().withBody(jsonResponse));

    SmsMessageStatus expected =
        SmsMessageStatus.builder()
            .messageId("messageId")
            .acceptedTime(Instant.parse("2021-07-29T13:45:33.404Z"))
            .from(FROM)
            .to(TO)
            .correlationId("correlationId")
            .content("Hello world!")
            .contentType(SmsContentType.TEXT)
            .dltTemplateId("dltTemplateId")
            .status(SendStatus.QUEUED)
            .statusTime(Instant.parse("2021-07-29T13:45:33.404Z"))
            .error(new ErrorResponse("7000", "error"))
            .requestId(REQUEST_ID)
            .build();

    SmsMessageStatus actual = client.getStatus("messageId");
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
