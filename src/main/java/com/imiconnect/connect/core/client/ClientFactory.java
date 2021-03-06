package com.imiconnect.connect.core.client;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

/**
 * Wraps the Apache CloseableHttpClient in a singleton so that instances of {@link
 * ApacheSyncInternalClient} use the same one. In addition, a shutdown hook is registered with the
 * JVM to ensure that the client is closed during normal shutdown procedures.
 */
enum ClientFactory {
  INSTANCE;

  private final CloseableHttpClient client;

  ClientFactory() {
    String version =
        Optional.ofNullable(this.getClass().getPackage().getImplementationVersion()).orElse("");
    this.client =
        HttpClientBuilder.create().setUserAgent("Connect SDK (Java " + version + ")").build();
    registerShutdownHook();
  }

  public CloseableHttpClient getInstance() {
    return client;
  }

  private void registerShutdownHook() {
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  if (client != null) {
                    try {
                      client.close();
                    } catch (IOException e) {
                      throw new UncheckedIOException(e);
                    }
                  }
                }));
  }
}
