package com.cisco.cpaas.core.client;

import com.cisco.cpaas.core.parser.JacksonParser;
import com.cisco.cpaas.core.parser.ObjectParser;

/**
 * Step style builder that constructs a {@link WebexClient} using the supplied configurations. The
 * required properties are defined using separate interfaces that chain together to ensure that they
 * are set at compile time. The remaining optional properties have default values set which can be
 * overridden as needed.
 */
public class ClientConfigurer {

  /** Defines the method to set the required URL property. */
  public interface UrlStep<T> {

    /**
     * Specify the root URL that points to the webex API without the API version part. i.e. <code>
     * https://mycompany.webex.com</code>
     */
    ApiTokenStep<T> withBaseUrl(String url);
  }

  /** Defines the method to set the required api authentication token property. */
  public interface ApiTokenStep<T> {

    /**
     * Specify the API token to use for authentication. This value can be found in the Connect
     * platform's web interface.
     */
    OptionalStep<T> withApiToken(String apiToken);
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

  /** Base implementation of the step interfaces that to construct a new {@link WebexClient}. */
  public abstract static class Steps<T> implements UrlStep<T>, ApiTokenStep<T>, OptionalStep<T> {

    protected String apiToken;
    protected String baseUrl;
    protected ObjectParser parser = new JacksonParser();

    @Override
    public ApiTokenStep<T> withBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    @Override
    public OptionalStep<T> withApiToken(String apiToken) {
      this.apiToken = apiToken;
      return this;
    }

    public abstract T build();
  }
}
