package com.imiconnect.cpaas.core.type;

/** Common interfaces for constructing a stepped builder. */
public interface MessageBuilder {

  /**
   * Builder step for adding content to the message.
   *
   * @param <T> The java type that represents the content.
   * @param <R> The return type after setting the content. Typically another builder step.
   */
  interface Content<T, R> {
    R content(T content);
  }

  /**
   * Builder step for adding content type to the message.
   *
   * @param <T> The java type that represents the content type.
   * @param <R> The return type after setting the content. Typically another builder step.
   */
  interface ContentType<T, R> {
    R contentType(T contentType);
  }

  /**
   * Builder step for adding the endpoint that the message is sent from. Typically a phone number or
   * short code value. This is automatically chained with the {@link MessageBuilder.To} interface to
   * ensure that both the from and to fields are set when using this interface.
   *
   * @param <R> The return type after setting the from and to values. Typically another builder
   *     step.
   */
  interface From<R> {
    To<R> from(String from);
  }

  /**
   * Builder step for adding the endpoint that the message should be sent to.
   *
   * @param <R> The return type after setting the to value. Typically another builder step.
   */
  interface To<R> {
    R to(String to);
  }
}
