package com.imiconnect.connect.voice.type;

/**
 * Collection of interfaces that are used to define the sequence of steps for creating a new {@link
 * Call}.
 */
public interface CallSteps {

  /** Step 1 add a from endpoint. */
  interface From<R> {
    /**
     * Add a from endpoint.
     *
     * @param from The endpoint that the message will be sent from.
     * @return Step 2
     */
    To<R> from(String from);
  }

  /** Step 2 add a send-to phone number. */
  interface To<R> {
    /**
     * Add a send-to phone number E.164 format required.
     *
     * @param to the send-to endpoint.
     * @return Options
     */
    R to(String to);
  }
}
