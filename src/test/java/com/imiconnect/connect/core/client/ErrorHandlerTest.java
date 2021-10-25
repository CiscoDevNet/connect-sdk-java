package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.parser.ObjectParser;
import com.imiconnect.connect.core.parser.ParseException;
import com.imiconnect.connect.core.type.ErrorResponse;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** Unit tests for the {@link ErrorHandler}. */
@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

  @Mock private ObjectParser parser;
  @Mock private ClassicHttpResponse response;
  @Mock private HttpEntity httpEntity;
  private ErrorHandler errorHandler;

  @BeforeEach
  public void init() {
    errorHandler = new ErrorHandler(parser);
  }

  @ParameterizedTest
  @MethodSource("exceptionByStatus")
  public void shouldThrowBadRequestException(
      int statusCode, Class<HttpResponseException> exceptionType) {
    when(parser.readToObject(any(), any())).thenReturn(new ErrorResponse("7000", "Error"));
    when(response.getCode()).thenReturn(statusCode);
    when(response.getEntity()).thenReturn(httpEntity);

    HttpResponseException e =
        assertThrows(exceptionType, () -> errorHandler.handleError(response, "requestId"));

    assertThat(e.getErrorCode(), equalTo("7000"));
    assertThat(e.getRequestId(), equalTo("requestId"));
    assertThat(e.getStatusCode(), equalTo(statusCode));
    assertThat(e.getMessage(), equalTo("7000 - Error"));
  }

  public static Stream<Arguments> exceptionByStatus() {
    return Stream.of(
        Arguments.of(400, BadRequestException.class),
        Arguments.of(401, AuthenticationException.class),
        Arguments.of(403, AuthenticationException.class),
        Arguments.of(429, RateLimitExceededException.class),
        Arguments.of(500, HttpServerErrorException.class),
        Arguments.of(503, HttpServerErrorException.class));
  }

  @Test
  public void shouldThrowResourceNotFoundOn404WithNoMessageBody() {
    when(response.getCode()).thenReturn(404);

    HttpResponseException e =
        assertThrows(
            ResourceNotFoundException.class, () -> errorHandler.handleError(response, "requestId"));

    assertThat(e.getRequestId(), equalTo("requestId"));
    assertThat(e.getStatusCode(), equalTo(404));
    assertThat(e.getMessage(), equalTo("404 - Resource not found."));
  }

  @Test
  public void shouldHandleInvalidResponse() {
    when(response.getCode()).thenReturn(503);
    when(response.getReasonPhrase()).thenReturn("Error");
    when(parser.readToObject(any(), any())).thenThrow(new ParseException("", null));
    when(response.getEntity()).thenReturn(httpEntity);

    HttpResponseException e =
        assertThrows(
            HttpResponseException.class, () -> errorHandler.handleError(response, "requestId"));

    assertThat(e.getRequestId(), equalTo("requestId"));
    assertThat(e.getStatusCode(), equalTo(503));
    assertThat(e.getMessage(), equalTo("503 - Error"));
    assertThat(e.getCause(), instanceOf(ParseException.class));
  }
}
