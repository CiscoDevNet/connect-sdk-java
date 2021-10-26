package com.imiconnect.connect.core.client;

/** Interface defining the common methods for all connect client types. */
public interface ConnectClient {

  /**
   * Refreshes the security token used by an API client. Implementations are thread safe. This would
   * typically be needed when using authentication methods that have short-lived tokens such as when
   * using JWT authentication.
   *
   * @param apiToken The new security token to use with the client.
   */
  void refreshToken(String apiToken);
}
