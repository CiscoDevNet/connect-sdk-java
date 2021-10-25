package com.imiconnect.connect.sms.type;

/** Represents the different type of content that can be sent as a text message. */
public enum SmsContentType {
  /** Normal text content without unicode. */
  TEXT,

  /** A text message with unicode chars that are outside the ASCII charset. */
  UNICODE,

  /** The content is a template ID. */
  TEMPLATE,

  /**
   * Binary content which can be used to trigger an arbitrary action on some supported messaging
   * clients. Actual content needs to be represented as a hexadecimal string when sending a request
   * to the API.
   */
  BINARY
}
