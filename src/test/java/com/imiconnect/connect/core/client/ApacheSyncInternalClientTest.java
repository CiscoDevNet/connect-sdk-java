package com.imiconnect.connect.core.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.imiconnect.connect.core.parser.JacksonParser;
import com.imiconnect.connect.core.type.IdempotentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serviceUnavailable;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.temporaryRedirect;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the {@link ApacheSyncInternalClient} that spins up a single wiremock server to test the
 * (de)serialization of data to and from a Map structure and send / receive it using the wrapped
 * apache http client.
 */
@WireMockTest
class ApacheSyncInternalClientTest {

  private static final String JSON_REQUEST = "{ \"type\": \"text\" }";
  private static final String JSON_RESPONSE = "{ \"id\": \"messageId\" }";
  private static final String AUTH_TOKEN = "authToken";
  private static final String IDEMPOTENCY_KEY = "idempotencyKey";

  private ApacheSyncInternalClient client;

  @BeforeEach
  public void init(WireMockRuntimeInfo wireMockRuntimeInfo) {
    String baseUrl = wireMockRuntimeInfo.getHttpBaseUrl();
    client = new ApacheSyncInternalClient(baseUrl, AUTH_TOKEN, new JacksonParser());
  }

  @Test
  public void shouldSendGetRequest() {
    stubFor(get("/messageId").willReturn(ok(JSON_RESPONSE)));
    MockResponse response = client.get("/messageId", MockResponse.class);
    assertThat(response, notNullValue());
    assertThat(response.getId(), equalTo("messageId"));
  }

  @Test
  public void shouldSendPostRequest() {
    stubFor(
        post("/")
            .withRequestBody(equalToJson(JSON_REQUEST))
            .withHeader(HttpHeaders.IDEMPOTENCY_KEY, WireMock.equalTo(IDEMPOTENCY_KEY))
            .willReturn(ok(JSON_RESPONSE)));

    MockPostRequest mockPost = new MockPostRequest();
    mockPost.put("type", "text");

    MockResponse response = client.post("", mockPost, MockResponse.class);

    assertThat(response, notNullValue());
    assertThat(response.getId(), equalTo("messageId"));
  }

  @ParameterizedTest
  @ValueSource(ints = {400, 499, 500, 599})
  public void shouldThrowExceptionOnNonSuccessResponseCode(int statusCode) {
    stubFor(
        get("/messageId")
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withBody("{ \"code\": \"7000\", \"message\": \"Error Message\" }")
                    .withHeader(HttpHeaders.REQUEST_ID, "requestId")));

    HttpResponseException e =
        assertThrows(
            HttpResponseException.class, () -> client.get("/messageId", MockResponse.class));

    assertThat(e.getErrorCode(), equalTo("7000"));
    assertThat(e.getStatusCode(), equalTo(statusCode));
    assertThat(e.getRequestId(), equalTo("requestId"));
    assertThat(e.getMessage(), equalTo("7000 - Error Message"));
  }

  @Test
  public void shouldThrowNotFoundWhenStatus404() {
    stubFor(
        get("/messageId")
            .willReturn(
                aResponse()
                    .withStatus(404)
                    .withBody("{ \"code\": \"7000\", \"message\": \"Error Message\" }")
                    .withHeader(HttpHeaders.REQUEST_ID, "requestId")));

    assertThrows(
        ResourceNotFoundException.class, () -> client.get("/messageId", MockResponse.class));
  }

  @Test
  public void shouldHandleEmptyResponseBodyOn404() {
    stubFor(
        get("/messageId")
            .willReturn(
                aResponse().withStatus(404).withHeader(HttpHeaders.REQUEST_ID, "requestId")));
    ResourceNotFoundException e =
        assertThrows(
            ResourceNotFoundException.class, () -> client.get("/messageId", MockResponse.class));
    assertThat(e.getErrorCode(), equalTo(null));
  }

  @ParameterizedTest
  @ValueSource(ints = {401, 403})
  public void shouldThrowAuthExceptionWhenStatus401Or403() {
    stubFor(
        get("/messageId")
            .willReturn(
                aResponse()
                    .withStatus(401)
                    .withBody("{ \"code\": \"7000\", \"message\": \"Auth\" }")
                    .withHeader(HttpHeaders.REQUEST_ID, "requestId")));

    assertThrows(AuthenticationException.class, () -> client.get("/messageId", MockResponse.class));
  }

  @Test
  public void shouldHandleErrorNotConformingToStandardErrorResponse() {
    stubFor(
        get("/resourceId")
            .willReturn(
                serviceUnavailable().withBody("<!DOCTYPE html><html><body></body></html>")));
    assertThrows(HttpResponseException.class, () -> client.get("/resourceId", MockResponse.class));
  }

  @Test
  public void shouldAppendAuthHeader() {
    stubFor(any(urlMatching(".*")).willReturn(ok("{}")));
    MockPostRequest mockPost = new MockPostRequest();
    client.post("", mockPost, MockResponse.class);
    WireMock.verify(
        postRequestedFor(urlEqualTo("/"))
            .withHeader(
                HttpHeaders.AUTHORIZATION,
                WireMock.equalTo(HttpHeaders.BEARER_PREFIX + AUTH_TOKEN)));

    client.get("/path", MockResponse.class);
    WireMock.verify(
        getRequestedFor(urlEqualTo("/path"))
            .withHeader(
                HttpHeaders.AUTHORIZATION,
                WireMock.equalTo(HttpHeaders.BEARER_PREFIX + AUTH_TOKEN)));
  }

  @Test
  public void shouldAppendIdempotencyKeyHeaderOnPost() {
    stubFor(any(urlMatching(".*")).willReturn(ok("{}")));
    MockPostRequest mockPost = new MockPostRequest();
    client.post("", mockPost, MockResponse.class);
    WireMock.verify(
        postRequestedFor(urlEqualTo("/"))
            .withHeader(HttpHeaders.IDEMPOTENCY_KEY, WireMock.equalTo(IDEMPOTENCY_KEY)));
  }

  @Test
  public void shouldHandleRedirect(WireMockRuntimeInfo wireMockInfo) {
    stubFor(
        any(urlMatching("/302"))
            .willReturn(temporaryRedirect(wireMockInfo.getHttpBaseUrl() + "/redirected")));
    stubFor(any(urlMatching("/redirected")).willReturn(ok("{}")));
    client.get("/302", MockResponse.class);

    WireMock.verify(getRequestedFor(urlEqualTo("/302")));
    WireMock.verify(getRequestedFor(urlEqualTo("/redirected")));
  }

  @Test
  public void shouldExtractRequestIdHeaderOnResponses() {
    stubFor(
        get("/path").willReturn(ok(JSON_RESPONSE).withHeader(HttpHeaders.REQUEST_ID, "requestId")));
    MockResponse response = client.get("/path", MockResponse.class);
    assertThat(response, notNullValue());
    assertThat(response.getRequestId(), equalTo("requestId"));
  }

  /** Simple mock request object used for testing. */
  private static class MockPostRequest extends HashMap<String, String>
      implements IdempotentRequest {
    @Override
    public String getIdempotencyKey() {
      return IDEMPOTENCY_KEY;
    }
  }

  /** Simple pojo to extend the response and allow integration testing of serialization. */
  private static class MockResponse extends ConnectResponse {

    private final String id;

    public MockResponse(String requestId, String id) {
      super(requestId);
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }
}
