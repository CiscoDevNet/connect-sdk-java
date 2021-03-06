package com.imiconnect.connect.core.client;

import com.imiconnect.connect.core.parser.JacksonParser;
import com.imiconnect.connect.core.parser.ObjectParser;
import com.imiconnect.connect.core.util.UrlUtils;

/**
 * Step style builder that constructs a {@link ConnectClient} using the supplied configurations. The
 * required properties are defined using separate interfaces that chain together to ensure that they
 * are set at compile time. The remaining optional properties have default values set which can be
 * overridden as needed.
 */
public class ClientConfigurer {

  /** Defines the method to set the required URL property. */
  public interface UrlStep<T> {

    /**
     * Specify the root URL that points to the Connect API without the API version part. i.e. <code>
     * https://mycompany.connect.com</code>
     */
    ApiTokenStep<T> withBaseUrl(String url);
  }

  /** Defines the method to set the required api authentication token property. */
  public interface ApiTokenStep<T> {

    /**
     * Specify the API key to use for authentication. This value can be found in the Connect
     * platform's web interface.
     */
    OptionalStep<T> withApiKey(String apiKey);
  }

  /** Defines the methods that can be used to set the optional properties. */
  public interface OptionalStep<T> {

    /**
     * Constructs the instance of the client with the provided configuration.
     *
     * @return The constructed client implementation.
     */
    T build();
  }

  /** Base implementation of the step interfaces that to construct a new {@link ConnectClient}. */
  public abstract static class Steps<T> implements UrlStep<T>, ApiTokenStep<T>, OptionalStep<T> {

    protected String apiToken;
    protected String baseUrl;
    protected ObjectParser parser = new JacksonParser();

    @Override
    public ApiTokenStep<T> withBaseUrl(String baseUrl) {
      this.baseUrl = UrlUtils.removeTrailingSlash(baseUrl);
      UrlUtils.validateUrl(baseUrl);
      return this;
    }

    @Override
    public OptionalStep<T> withApiKey(String apiToken) {
      this.apiToken = apiToken;
      return this;
    }

    public abstract T build();
  }
}
