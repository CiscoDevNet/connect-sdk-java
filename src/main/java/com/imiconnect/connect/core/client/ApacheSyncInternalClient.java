package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.ConnectException;
import com.imiconnect.connect.core.parser.ObjectParser;
import com.imiconnect.connect.core.parser.ParseException;
import com.imiconnect.connect.core.type.ErrorResponse;
import com.imiconnect.connect.core.type.Idempotent;
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
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_REDIRECTION;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;

/**
 * Synchronous Apache HTTP Client version of the {@link InternalClient} intended to be used
 * internally only.
 */
// TODO: Add retry backoff implementation
public class ApacheSyncInternalClient implements InternalClient {

  private String apiToken;
  private final String baseUrl;
  private final ObjectParser parser;
  private final CloseableHttpClient httpClient;

  public ApacheSyncInternalClient(String baseUrl, String apiToken, ObjectParser parser) {
    this.baseUrl = requireNonNull(baseUrl, "url can not be null.");
    this.apiToken = requireNonNull(apiToken, "API Token can not be null.");
    this.parser = requireNonNull(parser, "Object parser can not be null.");
    this.httpClient = ClientFactory.INSTANCE.getInstance();
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
      String path, Idempotent request, Class<R> responseType) {
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
      if (response.getCode() == SC_UNAUTHORIZED || response.getCode() == SC_FORBIDDEN) {
        throw new AuthenticationException(requestId, response.getCode(), parseError(is));
      }
      if (response.getCode() == SC_NOT_FOUND) {
        throwOnNotFound(is, requestId);
      }
      if (isError(response)) {
        throw new HttpResponseException(requestId, response.getCode(), parseError(is));
      }

      R parsedResponse = parser.readToObject(is, responseType);
      parsedResponse.setRequestId(requestId);
      return parsedResponse;
    }
  }

  private boolean isError(HttpResponse response) {
    return response.getCode() < SC_OK || response.getCode() >= SC_REDIRECTION;
  }

  private ErrorResponse parseError(InputStream is) {
    return parser.readToObject(is, ErrorResponse.class);
  }

  private void setAuthentication(HttpUriRequestBase request) {
    request.setHeader(AUTHORIZATION, BEARER_PREFIX + apiToken);
  }

  private String extractRequestId(HttpResponse response) {
    Header requestId = response.getLastHeader(REQUEST_ID);
    return requestId != null ? requestId.getValue() : null;
  }

  /**
   * Some of the 404 responses may not contain a request body causing jackson to throw an exception.
   * Since its not easily possible to check if the input stream is empty or not, this is a work
   * around to only catch the parse exception when the HTTP status code is 404 and handle it that
   * way.
   *
   * @param is The response input stream.
   * @param requestId the request id associated with the response.
   */
  private void throwOnNotFound(InputStream is, String requestId) {
    ErrorResponse response = null;
    try {
      response = parseError(is);
    } catch (ParseException e) {
      // Do nothing. This is for the case when there is no response body on 404's
    }
    throw new ResourceNotFoundException(requestId, response);
  }
}
