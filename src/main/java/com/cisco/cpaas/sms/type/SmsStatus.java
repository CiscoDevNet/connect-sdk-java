package com.cisco.cpaas.sms.type;

/** Available values that represent the state of the message send attempt. */
public enum SmsStatus {
  QUEUED,
  SENT,
  DELIVERED,
  FAILED
}
