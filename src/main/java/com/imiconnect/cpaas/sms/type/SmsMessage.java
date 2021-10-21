package com.imiconnect.cpaas.sms.type;

import com.imiconnect.cpaas.core.type.Idempotent;
import com.imiconnect.cpaas.core.type.MessageBuilder;
import com.imiconnect.cpaas.core.type.PhoneNumber;

import java.net.URI;
import java.util.Map;

import static com.imiconnect.cpaas.core.util.UnicodeDetector.containsUnicode;
import static com.imiconnect.cpaas.sms.type.SmsContentType.TEMPLATE;
import static com.imiconnect.cpaas.sms.type.SmsContentType.TEXT;
import static com.imiconnect.cpaas.sms.type.SmsContentType.UNICODE;
import static java.util.Objects.requireNonNull;

/** API to interact with SMS message requests. */
public interface SmsMessage extends Idempotent {

  String getFrom();

  PhoneNumber getTo();

  String getContent();

  SmsContentType getContentType();

  Map<String, String> getSubstitutions();

  String getCorrelationId();

  String getDltTemplateId();

  URI getCallbackUrl();

  String getCallbackData();


  /**
   * Starts building a new message that is based off of a template ID. The content type will
   * automatically be set to {@link SmsContentType#TEMPLATE}.
   *
   * @param templateId The message template to use.
   */
  public static MessageBuilder.From<Options> fromTemplate(String templateId) {
    requireNonNull(templateId, "templateId can not be null.");
    return new SmsMessageRequest.Builder(templateId, TEMPLATE);
  }

  /**
   * Starts building a new message consisting of text or unicode content. This method will attempt
   * to determine if the string contains unicode literal characters and will set the content type to
   * either {@link SmsContentType#TEXT} or {@link SmsContentType#UNICODE} appropriately.
   *
   * <p>The unicode detection can be bypassed by using the overloaded factory method {@link
   * #of(String, SmsContentType)}. This may slightly improve performance or may be needed if
   * detection is not working as expected.
   *
   * @param content The content to send as the SMS message.
   */
  // TODO: Update docs for links to content detection etc.
  public static MessageBuilder.From<Options> of(String content) {
    SmsContentType contentType = containsUnicode(content) ? UNICODE : TEXT;
    return SmsMessage.of(content, contentType);
  }

  /**
   * Starts building a new message of the specified content type.
   *
   * @param content the content to be used as the message.
   * @param type The type of message content. Typically this would be one of {@link
   *     SmsContentType#UNICODE}, {@link SmsContentType#TEXT}, or {@link SmsContentType#TEMPLATE}
   *     with this method.
   */
  public static MessageBuilder.From<Options> of(String content, SmsContentType type) {
    if (content.length() > 1024) {
      throw new IllegalArgumentException("message content can not have more than 1024 characters.");
    }
    return new SmsMessageRequest.Builder(content, type);
  }

  /**
   * Starts building a new message consisting of binary content represented as a byte array. The
   * content type will automatically be set to {@link SmsContentType#BINARY}.
   *
   * @param content The binary data to send
   */
  public static MessageBuilder.From<Options> of(byte[] content) {
    StringBuilder sb = new StringBuilder();
    for (byte b : content) {
      sb.append(Integer.toHexString(b));
    }
    return new SmsMessageRequest.Builder(sb.toString(), SmsContentType.BINARY);
  }


  /** Defines the optional values that can be set on a sms message. */
  interface Options {

    /**
     * Optional URL where a notification callback can be received.
     *
     * @param callbackUrl The URL specifying where the callback webhook can be accessed.
     * @return Options
     */
    Options callbackUrl(String callbackUrl);

    /**
     * Optional URL where a notification callback can be received.
     *
     * @param callbackUrl The URL specifying where the callback webhook can be accessed.
     * @return Options
     */
    Options callbackUrl(URI callbackUrl);

    /**
     * Optional data that can be included in the callback request. This field is ignored if no
     * {@link #callbackUrl(URI)} is specified.
     *
     * @param callbackData The data to include in the callback.
     * @return Options
     */
    Options callbackData(String callbackData);

    /**
     * An optional, arbitrary identifier to track the message.
     *
     * @param correlationId The identifier.
     * @return Options
     */
    Options correlationId(String correlationId);

    /**
     * Specifies the DLT template ID used for this message. This is only used in certain regions.
     *
     * @param dltTemplateId The id.
     * @return Options
     */
    Options dltTemplateId(String dltTemplateId);

    /**
     * a substitution is used to replace placeholders within the content or template specified.
     *
     * @param key The templated key.
     * @param val The replacement value.
     * @return Options
     */
    Options substitution(String key, String val);

    /**
     * Adds all substitutions in the specified map with any existing substitutions, where a
     * substitution is used to replace placeholders within the content or template specified.
     *
     * @param substitutions The map of key - value pairs.
     * @return Options
     */
    Options substitutions(Map<String, String> substitutions);

    SmsMessageRequest build();
  }
}
