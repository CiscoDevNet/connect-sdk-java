package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.ConnectException;
import com.imiconnect.connect.core.parser.ObjectParser;
import com.imiconnect.connect.core.type.IdempotentRequest;
import org.apache.commons.codec.CharEncoding;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;

import java.io.IOException;
import java.io.InputStream;

import static com.imiconnect.connect.core.client.HttpHeaders.AUTHORIZATION;
import static com.imiconnect.connect.core.client.HttpHeaders.BEARER_PREFIX;
import static com.imiconnect.connect.core.client.HttpHeaders.IDEMPOTENCY_KEY;
import static com.imiconnect.connect.core.client.HttpHeaders.REQUEST_ID;
import static java.util.Objects.requireNonNull;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_REDIRECTION;

/**
 * Synchronous Apache HTTP Client version of the {@link InternalClient} intended to be used
 * internally only.
 */
// TODO: Add retry backoff implementation, and see about making it package private
public class ApacheSyncInternalClient implements InternalClient {

  private String apiToken;
  private final String baseUrl;
  private final ObjectParser parser;
  private final CloseableHttpClient httpClient;
  private final ErrorHandler errorHandler;

  public ApacheSyncInternalClient(String baseUrl, String apiToken, ObjectParser parser) {
    this.baseUrl = requireNonNull(baseUrl, "url can not be null.");
    this.apiToken = requireNonNull(apiToken, "API Token can not be null.");
    this.parser = requireNonNull(parser, "Object parser can not be null.");
    this.httpClient = ClientFactory.INSTANCE.getInstance();
    this.errorHandler = new ErrorHandler(parser);
  }

  public void refreshToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public <R extends ConnectResponse> R get(String path, Class<R> responseType) {
    HttpGet get = new HttpGet(baseUrl + path);
    try {
      R response = exchange(get, responseType);
      return response;
    } catch (ConnectException e) {
      throw e;
    } catch (Exception e) {
      throw new ConnectException(e);
    }
  }

  public <R extends ConnectResponse> R post(
      String path, IdempotentRequest request, Class<R> responseType) {
    HttpPost post = new HttpPost(baseUrl + path);
    try {
      byte[] bytes = parser.writeValueAsBytes(request);
      post.setEntity(new ByteArrayEntity(bytes, ContentType.APPLICATION_JSON, CharEncoding.UTF_8));
      post.setHeader(IDEMPOTENCY_KEY, request.getIdempotencyKey());

      R response = exchange(post, responseType);
      if (response == null) {
        throw new ConnectException("Unknown error getting response from connect");
      }
      return response;
    } catch (ConnectException e) {
      throw e;
    } catch (Exception e) {
      throw new ConnectException(e);
    }
  }

  private <R extends ConnectResponse> R exchange(HttpUriRequestBase request, Class<R> responseType)
      throws IOException {

    setAuthentication(request);
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      InputStream is = response.getEntity().getContent();

      String requestId = extractRequestId(response);

      if (isError(response)) {
        errorHandler.handleError(response, requestId);
      }

      R parsedResponse = parser.readToObject(is, responseType);
      parsedResponse.setRequestId(requestId);
      return parsedResponse;
    }
  }

  private boolean isError(HttpResponse response) {
    return response.getCode() < SC_OK || response.getCode() >= SC_REDIRECTION;
  }

  private void setAuthentication(HttpUriRequestBase request) {
    request.setHeader(AUTHORIZATION, BEARER_PREFIX + apiToken);
  }

  private String extractRequestId(HttpResponse response) {
    Header requestId = response.getLastHeader(REQUEST_ID);
    return requestId != null ? requestId.getValue() : null;
  }
}
