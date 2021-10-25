package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.parser.ObjectParser;
import com.imiconnect.connect.core.parser.ParseException;
import com.imiconnect.connect.core.type.ErrorResponse;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_TOO_MANY_REQUESTS;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;

/**
 * Processes a ClassicHttpResponse that was determined to be considered unsuccessful. An exception
 * will always be thrown based on the parameters of the response object.
 */
class ErrorHandler {

  private final ObjectParser parser;

  ErrorHandler(ObjectParser parser) {
    this.parser = parser;
  }

  /**
   * Throws an exception based on the status code and existence of the response node of the body.
   *
   * @param response The response received from the web service.
   * @param requestId The unique request id associated with the request.
   * @throws HttpResponseException for successfully processed errors.
   * @throws IOException when the response content input stream can not be read.
   */
  void handleError(ClassicHttpResponse response, String requestId) throws IOException {

    int statusCode = response.getCode();
    if (statusCode == SC_NOT_FOUND) {
      throw new ResourceNotFoundException(requestId);
    }

    ErrorResponse error = parseError(response, requestId);
    String msg = isNull(error) ? constructDefaultErrorMsg(response) : error.getMessage();
    String errorCode = isNull(error) ? null : error.getCode();

    if (statusCode == SC_UNAUTHORIZED || statusCode == SC_FORBIDDEN) {
      throw new AuthenticationException(requestId, statusCode, msg, errorCode);
    } else if (statusCode == SC_BAD_REQUEST) {
      throw new BadRequestException(requestId, msg, errorCode);
    } else if (statusCode == SC_TOO_MANY_REQUESTS) {
      throw new RateLimitExceededException(requestId, msg, errorCode);
    } else if (statusCode >= SC_INTERNAL_SERVER_ERROR) {
      throw new HttpServerErrorException(requestId, statusCode, msg, errorCode);
    } else {
      throw new HttpResponseException(requestId, statusCode, msg, errorCode);
    }
  }

  private ErrorResponse parseError(ClassicHttpResponse response, String rid) throws IOException {
    try {
      return parser.readToObject(response.getEntity().getContent(), ErrorResponse.class);
    } catch (ParseException e) {
      throw new HttpResponseException(rid, response.getCode(), response.getReasonPhrase(), e);
    }
  }

  private String constructDefaultErrorMsg(ClassicHttpResponse response) {
    return String.format("%s - %s", response.getCode(), response.getReasonPhrase());
  }
}
