package com.cisco.cpaas.sms.type;

import com.cisco.cpaas.core.type.Idempotent;
import com.cisco.cpaas.core.type.PhoneNumber;

import java.net.URI;
import java.util.Map;

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
