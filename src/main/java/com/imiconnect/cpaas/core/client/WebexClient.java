package com.imiconnect.cpaas.core.client;

/** Interface defining the common methods for all webex client types. */
public interface WebexClient {

  /**
   * Refreshes the security token used by an API client. Implementations are thread safe. This would
   * typically be needed when using authentication methods that have short-lived tokens such as when
   * using JWT authentication.
   *
   * @param apiToken The new security token to use with the client.
   */
  void refreshToken(String apiToken);
}
